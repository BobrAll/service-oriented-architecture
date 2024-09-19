import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RouteService } from '../../core/services/route.service';

@Component({
    selector: 'app-route-detail',
    templateUrl: './route-detail.component.html',
    styleUrls: ['./route-detail.component.scss']
})
export class RouteDetailComponent implements OnInit {
    route: any;
    routeId: number;

    constructor(private routeService: RouteService, private routeParams: ActivatedRoute) {}

    ngOnInit(): void {
        this.routeId = this.routeParams.snapshot.params['id'];
        this.getRouteById();
    }

    getRouteById(): void {
        if (this.routeId) {
            this.routeService.getRouteById(this.routeId).subscribe(
                (route) => {
                    this.route = route;
                },
                (error) => console.error('Error fetching route:', error)
            );
        }
    }
}