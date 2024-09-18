import { Component } from '@angular/core';
import { NavigatorService } from '../../core/services/navigator.service';
import { Router } from '@angular/router'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'

@Component({
    selector: 'app-shortest-route',
    templateUrl: './shortest-route.component.html',
    styleUrls: ['./shortest-route.component.scss']
})
export class ShortestRouteComponent {
    fromId: string;
    toId: string;
    shortestRoute: any;
    shortestRouteForm: FormGroup;
    constructor(private fb: FormBuilder, private navigatorService: NavigatorService, private router: Router) {
        this.createForm();
    }

    createForm() {
        this.shortestRouteForm = this.fb.group({
            fromId: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
            toId: ['', [Validators.required, Validators.pattern(/^\d+$/)]]
        });
    }

    findShortestRoute(): void {
        this.navigatorService.findShortestRoute(this.fromId, this.toId).subscribe(data => {
            this.shortestRoute = data;
        });
    }

    goBack(): void {
        this.router.navigate(['/routes']);
    }
}