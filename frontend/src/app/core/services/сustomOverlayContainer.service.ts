import { OverlayContainer } from '@angular/cdk/overlay';
import { Injectable } from '@angular/core';

@Injectable()
export class CustomOverlayContainer extends OverlayContainer {
    _createContainer(): void {
        const container = document.createElement('div');
        container.classList.add('cdk-overlay-container');
        document.body.appendChild(container);
        this._containerElement = container;
    }
}
