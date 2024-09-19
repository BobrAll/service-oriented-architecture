import { Component, OnInit } from '@angular/core';
import { LocationService } from '../../core/services/location.service';

@Component({
    selector: 'app-location-list',
    templateUrl: './location-list.component.html',
    styleUrls: ['./location-list.component.scss']
})
export class LocationListComponent implements OnInit {
    locations: any[] = [];

    constructor(private locationService: LocationService) {}

    ngOnInit(): void {
        this.locationService.getAllLocations().subscribe(data => {
            this.locations = data;
        });
    }
}