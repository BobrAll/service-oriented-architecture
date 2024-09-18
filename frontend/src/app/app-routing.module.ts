import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { LocationsComponent } from './features/locations/locations.component';
import { RoutesComponent } from './features/routes/routes.component';
import { RouteListComponent } from './features/route-list/route-list.component'
import { RouteAddComponent } from './features/route-add/route-add.component'
import { RouteDetailComponent } from './features/route-detail/route-detail.component'
import { ShortestRouteComponent } from './features/shortest-route/shortest-route.component'
import { LocationListComponent } from './features/location-list/location-list.component'

const routes: Routes = [
    { path: '', component: DashboardComponent },
    { path: 'routes', component: RoutesComponent },
    { path: 'routes/list', component: RouteListComponent },
    { path: 'routes/add', component: RouteAddComponent },
    { path: 'routes/:id', component: RouteDetailComponent },
    { path: 'routes/distance/shortest', component: ShortestRouteComponent },
    { path: 'locations', component: LocationsComponent },
    { path: 'locations/list', component: LocationListComponent },
    { path: '**', redirectTo: '' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }