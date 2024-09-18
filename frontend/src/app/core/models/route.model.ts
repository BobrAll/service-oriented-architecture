import { Coordinates } from './coordinates.model';
import { Location } from './location.model';

export interface Route {
    id: number;
    name: string;
    coordinates: Coordinates;
    from: Location;
    to: Location;
    distance: number;
    creationDate: Date;
}