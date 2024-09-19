import { Component, OnInit } from '@angular/core';
import { LocationService } from '../../core/services/location.service'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'

@Component({
    selector: 'app-locations',
    templateUrl: './locations.component.html',
    styleUrls: ['./locations.component.scss']
})
export class LocationsComponent implements OnInit {
    locations: any[] = [];
    newLocation: any = { x: null, y: null, z: null };
    newLocationForm: FormGroup;
    selectedLocationForm: FormGroup;
    selectedLocation: any = null;
    errorMessage: string = '';

    constructor(
        private locationService: LocationService,
        private fb: FormBuilder
    ) {
        this.newLocationForm = this.fb.group({
            x: ['', [Validators.required, Validators.pattern(/^\d+(\.\d+)?$/)]],
            y: ['', [Validators.required, Validators.pattern(/^\d+(\.\d+)?$/)]],
            z: ['', [Validators.required, Validators.pattern(/^\d+(\.\d+)?$/)]],
        });

        this.selectedLocationForm = this.fb.group({
            x: ['', [Validators.required, Validators.pattern(/^\d+(\.\d+)?$/)]],
            y: ['', [Validators.required, Validators.pattern(/^\d+(\.\d+)?$/)]],
            z: ['', [Validators.required, Validators.pattern(/^\d+(\.\d+)?$/)]],
        });
    }

    ngOnInit(): void {
        this.getAllLocations();
    }

    getAllLocations(): void {
        this.locationService.getAllLocations().subscribe(
            (data) => (this.locations = data),
            (error) => this.handleError(error)
        );
    }

    addLocation(): void {
        if (this.newLocationForm.invalid) {
            this.errorMessage = 'Пожалуйста, введите корректные данные.';
            return;
        }

        this.locationService.addLocation(this.newLocationForm.value).subscribe(
            () => {
                this.errorMessage = '';
                this.newLocationForm.reset();
                this.getAllLocations();
            },
            (error) => this.handleError(error)
        );
    }

    deleteLocation(id: number): void {
        this.locationService.getLocationById(id).subscribe(
            () => {
                    this.locationService.deleteLocation(id).subscribe(
                        () => {
                            console.log(`Location with id ${id} deleted successfully`);
                            this.getAllLocations(); // Обновляем список локаций после удаления
                        },
                        (error) => this.handleError(error)
                    );
            },
            (error) => {
                this.errorMessage = 'Error updating location. Please check your data and try again.';
                console.error('Error updating location:', error);
            }
            );
    }

    editLocation(location: any): void {
        this.selectedLocation = { ...location };
        this.selectedLocationForm.patchValue(this.selectedLocation);
    }

    updateLocation(): void {
        if (!this.selectedLocation || this.selectedLocationForm.invalid) {
            this.errorMessage = 'Пожалуйста, введите корректные данные.';
            return;
        }

        this.locationService
            .updateLocation(this.selectedLocation.id, this.selectedLocationForm.value)
            .subscribe(
                () => {
                    this.errorMessage = '';
                    this.getAllLocations();
                    this.selectedLocation = null;
                },
                (error) => this.handleError(error)
            );
    }

    handleError(error: any): void {
        if (error.status === 0) {
            this.errorMessage = 'Не удается подключиться к серверу. Проверьте подключение к интернету.';
        } else if (error.status >= 400 && error.status < 500) {
            if (error.status === 400) {
                this.errorMessage = 'Ошибка: Введены некорректные данные. Проверьте данные и повторите попытку.';
            } else if (error.status === 404) {
                this.errorMessage = 'Ошибка: Ресурс не найден. Возможно, элемент был удален или перемещен.';
            } else {
                this.errorMessage = 'Ошибка: Некорректный запрос. Проверьте данные и повторите попытку.';
            }
        } else if (error.status >= 500) {
            this.errorMessage = 'Ошибка сервера. Попробуйте позже или обратитесь в службу поддержки.';
        } else {
            this.errorMessage = 'Неизвестная ошибка. Пожалуйста, повторите попытку.';
        }
    }

    cancelEdit(): void {
        this.selectedLocation = null;
        this.errorMessage = '';
        this.selectedLocationForm.reset();
    }
}