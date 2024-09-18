import { Component, OnInit } from '@angular/core';
import { RouteService } from '../../core/services/route.service';
import { Router } from '@angular/router'
import { NavigatorService } from '../../core/services/navigator.service'


@Component({
    selector: 'app-route-list',
    templateUrl: './route-list.component.html',
    styleUrls: ['./route-list.component.scss']
})
export class RouteListComponent implements OnInit {
    routes: any[] = [];
    distances: any[] = [];
    selectedRoute: any = null;
    currentSortField: string = '';
    sortDirection: 'asc' | 'desc' = 'asc';
    showUniqueDistances: boolean = false;
    filteredRoutes: any[] = [];

    minDistance: number | null = 0;
    maxDistance: number | null = 100000;

    constructor(private routeService: RouteService,
                private router: Router, private navigatorService: NavigatorService) {
        this.routeService.refreshNeeded$.subscribe(() => {
            this.getAllRoutes();
        });

        this.navigatorService.refreshNeeded$.subscribe(() => {
            this.getAllRoutes();
        });
    }

    ngOnInit(): void {
        this.getAllRoutes();
    }

    getAllRoutes(): void {
        this.minDistance = 0;
        this.maxDistance = 100000;

        this.routeService.getAllRoutes().subscribe(
            (routes) => {
                this.routes = routes;
                this.applyFilterAndSort();
            },
            (error) => console.error('Error fetching routes:', error)
        );
    }

    applyFilterAndSort(): void {
        this.applyFilter();
        let filteredRoutes = [...this.routes];
        if (this.showUniqueDistances) {
            this.routeService.getUniqueDistances().subscribe(
                (distances) => {
                    this.distances = distances;
                    console.log('Unique Distances:', this.distances);

                    const uniqueDistances = new Set<number>();

                    filteredRoutes = filteredRoutes.filter(route => {
                        const routeDistance = parseFloat(route.distance);

                        if (!uniqueDistances.has(routeDistance) && this.distances.includes(routeDistance)) {
                            uniqueDistances.add(routeDistance);
                            console.log('Adding unique route with distance:', routeDistance);
                            return true;
                        }

                        console.log('Duplicate route or non-unique distance found:', routeDistance);
                        return false;
                    });

                    this.sortRoutes(filteredRoutes);
                },
                (error) => console.error('Error fetching unique distances:', error)
            );
        } else {
            this.applyFilter();
        }
    }

    sortRoutes(filteredRoutes: any[]): void {
        if (this.currentSortField === 'distance') {
            filteredRoutes.sort((a, b) => this.sortDirection === 'asc' ? a.distance - b.distance : b.distance - a.distance);
        } else if (this.currentSortField === 'name') {
            filteredRoutes.sort((a, b) => this.sortDirection === 'asc' ? a.name.localeCompare(b.name) : b.name.localeCompare(a.name));
        }

        this.filteredRoutes = filteredRoutes;
    }


    deleteRoute(id: number): void {
        const routeToDelete = this.routes.find(route => route.id === id);
        if (!routeToDelete) {
            console.error(`Route with id ${id} does not exist.`);
            return;
        }

        this.routeService.deleteRoute(id).subscribe(
            (response) => {
                console.log('Delete response:', response);
                console.log(`Route with id ${id} deleted successfully`);
                this.getAllRoutes();
                this.routes = this.routes.filter(route => route.id !== id);
                console.log('Updated routes list:', this.routes);
            },
            (error) => console.error('Error deleting route:', error)
        );
    }

    viewRoute(id: number): void {
        this.routeService.getRouteById(id).subscribe(
            (route) => {
                console.log(`Viewing route with id ${id}`, route);
                this.router.navigate([`/routes/${id}`]);
            },
            (error) => console.error('Error fetching route:', error)
        );
    }

    editRoute(route: any): void {
        this.selectedRoute = { ...route };
    }

    updateRoute(): void {
        if (!this.selectedRoute) return;

        this.routeService.updateRoute(this.selectedRoute.id, this.selectedRoute).subscribe(
            () => {
                console.log(`Route with id ${this.selectedRoute.id} updated successfully`);
                this.getAllRoutes();
                this.selectedRoute = null;
            },
            (error) => console.error('Error updating route:', error)
        );
    }

    cancelEdit(): void {
        this.selectedRoute = null;
    }

    toggleSort(field: string): void {
        if (this.currentSortField === field) {
            this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
        } else {
            this.currentSortField = field;
            this.sortDirection = 'asc';
        }
        this.applyFilterAndSort();
    }

    getSortDirectionIcon(field: string): string {
        if (this.currentSortField === field) {
            return this.sortDirection === 'asc' ? 'arrow_upward' : 'arrow_downward';
        }
        return 'unfold_more';
    }

    applyFilter(): void {
        const min = this.minDistance !== null ? this.minDistance : 0;
        const max = this.maxDistance !== null ? this.maxDistance : 100000;

        this.routeService.getRoutesLessThan(max).subscribe(
            (lessThanRoutes) => {
                this.routeService.getRoutesGreaterThan(min).subscribe(
                    (greaterThanRoutes) => {
                        let combinedRoutes = lessThanRoutes.filter(route =>
                            greaterThanRoutes.some(gr => gr.id === route.id)
                        );

                        if (this.showUniqueDistances) {
                            const uniqueRoutesSet = new Set<number>();
                            combinedRoutes = combinedRoutes.filter(route => {
                                const routeDistance = parseFloat(route.distance);
                                if (!uniqueRoutesSet.has(routeDistance)) {
                                    uniqueRoutesSet.add(routeDistance);
                                    return true;
                                }
                                return false;
                            });
                        }

                        this.filteredRoutes = combinedRoutes;
                        this.sortRoutes(this.filteredRoutes);
                    },
                    (error) => console.error('Error fetching greater than routes:', error)
                );
            },
            (error) => console.error('Error fetching less than routes:', error)
        );
    }

}