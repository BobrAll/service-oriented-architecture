import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { HeaderComponent } from './layouts/header/header.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { ContainerComponent } from './layouts/container/container.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { LocationsComponent } from './features/locations/locations.component';
import { RoutesComponent } from './features/routes/routes.component';
import { NavigatorService } from './core/services/navigator.service';
import { RouteService } from './core/services/route.service';
import { ShortestRouteComponent } from './features/shortest-route/shortest-route.component'
import { RouteListComponent } from './features/route-list/route-list.component'
import { RouteDetailComponent } from './features/route-detail/route-detail.component'
import { RouteAddComponent } from './features/route-add/route-add.component'
import { LocationListComponent } from './features/location-list/location-list.component'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatInputModule } from '@angular/material/input'
import { MatButtonModule } from '@angular/material/button'
import { MatTableModule } from '@angular/material/table'
import { MatIconModule } from '@angular/material/icon'
import { MatSelectModule } from '@angular/material/select'
import { MatCardModule } from '@angular/material/card'
import { MatDialogModule } from '@angular/material/dialog';
import { OverlayContainer } from '@angular/cdk/overlay';
import { CustomOverlayContainer } from './core/services/сustomOverlayContainer.service'
import { MatCheckboxModule } from '@angular/material/checkbox'

// App Routes
const appRoutes: Routes = [
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
    declarations: [
        HeaderComponent,
        FooterComponent,
        ContainerComponent,
        DashboardComponent,
        LocationsComponent,
        RoutesComponent,
        ShortestRouteComponent,
        RouteListComponent,
        RouteDetailComponent,
        RouteAddComponent,
        LocationListComponent,
    ],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        RouterModule.forRoot(appRoutes),
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatTableModule,
        MatIconModule,
        MatSelectModule,
        MatCardModule,
        MatDialogModule,
        MatCheckboxModule,
    ],
    providers: [
        NavigatorService,
        RouteService,
        { provide: OverlayContainer, useClass: CustomOverlayContainer }
    ],
    bootstrap: [ContainerComponent]
})
export class AppModule { }
