import { Component, OnInit } from '@angular/core';
import { Route } from '../../core/models/route.model';
import { RouteService } from '../../core/services/route.service';

@Component({
    selector: 'app-routes',
    templateUrl: './routes.component.html',
    styleUrls: ['./routes.component.scss']
})
export class RoutesComponent implements OnInit {
    routes: Route[] = [];

    constructor(private routeService: RouteService) { }

    ngOnInit(): void {
        this.routeService.getAllRoutes().subscribe(data => {
            this.routes = data;
        });
    }
}