import { Component } from '@angular/core';
import { NavigatorService } from '../../core/services/navigator.service'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { RouteService } from '../../core/services/route.service'

@Component({
    selector: 'app-route-add',
    templateUrl: './route-add.component.html',
    styleUrls: ['./route-add.component.scss']
})
export class RouteAddComponent {
    routeRequest: any = {
        name: '',
        coordinates: { x: null, y: null },
        fromId: null,
        toId: null,
        distance: null
    };

    routeForm: FormGroup;

    constructor(private navigatorService: NavigatorService,
                private fb: FormBuilder,
                private  routeService: RouteService) {
        this.createForm();
    }

    createForm() {
        this.routeForm = this.fb.group({
            name: ['', Validators.required],
            coordinates: this.fb.group({
                x: [null, [Validators.required, Validators.pattern(/^\d+$/)]],
                y: [null, [Validators.required, Validators.pattern(/^\d+$/)]]
            }),
            fromId: [null, [Validators.required, Validators.pattern(/^\d+$/)]],
            toId: [null, [Validators.required, Validators.pattern(/^\d+$/)]],
            distance: [null, [Validators.required, Validators.min(1)]]
        });
    }

    addRoute(): void {
        if (this.routeForm.valid) {
            this.routeRequest = {
                name: this.routeForm.value.name,
                coordinates: {
                    x: this.routeForm.value.coordinates.x,
                    y: this.routeForm.value.coordinates.y
                },
                fromId: this.routeForm.value.fromId,
                toId: this.routeForm.value.toId,
                distance: this.routeForm.value.distance
            };

            this.routeService.createRoute(
                this.routeRequest
            ).subscribe(response => {
                console.log('Route added using Navigator Endpoint:', response);
            }, error => {
                console.error('Error adding route:', error);
            });
        } else {
            console.error('Form is invalid');
        }
    }

}