import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable, Subject } from 'rxjs'


@Injectable({
    providedIn: 'root'
})
export class RouteService {
    private baseUrl = 'https://localhost:8443/api/v1/routes';
    private refreshNeeded = new Subject<void>();
    constructor(private http: HttpClient) { }

    getAllRoutes(params?: any): Observable<any[]> {
        let httpParams = new HttpParams();
        if (params) {
            Object.keys(params).forEach(key => {
                if (params[key] !== undefined && params[key] !== null) {
                    httpParams = httpParams.set(key, params[key]);
                }
            });
        }

        return this.http.get(this.baseUrl, { params: httpParams, responseType: 'text' })
            .pipe(
                map((xmlString: string) => this.parseXmlListToJson(xmlString))
            );
    }

    getRouteById(id: number): Observable<any> {
        const url = `${this.baseUrl}/${id}`;
        return this.http.get(url, { responseType: 'text' })
            .pipe(
                map((xmlString: string) => this.parseXmlItemToJson(xmlString))
            );
    }

    get refreshNeeded$() {
        return this.refreshNeeded.asObservable();
    }

    updateRoute(id: number, routeRequest: any): Observable<any> {
        const url = `${this.baseUrl}/${id}`;
        return this.http.put(url, routeRequest, { responseType: 'text' })
            .pipe(
                map((xmlString: string) => this.parseXmlItemToJson(xmlString))
            );
    }

    deleteRoute(id: number): Observable<any> {
        const url = `${this.baseUrl}/${id}`;
        return this.http.delete(url, { responseType: 'text' })
            .pipe(
                map((xmlString: string) => {
                    console.log('Raw XML response:', xmlString);
                    const jsonResult = this.parseXmlItemToJson(xmlString);
                    console.log('Parsed JSON response:', jsonResult);
                    return jsonResult;
                })
            );
    }

    getRoutesLessThan(distance: number): Observable<any[]> {
        const url = `${this.baseUrl}/distance/less-than/${distance}`;
        return this.http.get(url, { responseType: 'text' })
            .pipe(
                map((xmlString: string) => this.parseXmlListToJson(xmlString))
            );
    }

    getRoutesGreaterThan(distance: number): Observable<any[]> {
        const url = `${this.baseUrl}/distance/greater-than/${distance}`;
        return this.http.get(url, { responseType: 'text' })
            .pipe(
                map((xmlString: string) => this.parseXmlListToJson(xmlString))
            );
    }



    getUniqueDistances(): Observable<number[]> {
        const url = `${this.baseUrl}/distance/unique`;
        return this.http.get(url, { responseType: 'text' }).pipe(
            map((xmlString: string) => this.parseUniqueDistances(xmlString))
        );
    }

    private parseXmlItemToJson(xmlString: string): any {
        const parser = new DOMParser();
        const xml = parser.parseFromString(xmlString, 'application/xml');
        const routeXml = xml.getElementsByTagName('Route')[0]; // Мы ожидаем, что это один Route

        if (!routeXml) return null; // Если нет данных, возвращаем null

        const parseLocation = (element: Element): any => ({
            id: element.getElementsByTagName('id')[0]?.textContent || 'N/A',
            x: element.getElementsByTagName('x')[0]?.textContent || 'N/A',
            y: element.getElementsByTagName('y')[0]?.textContent || 'N/A',
            z: element.getElementsByTagName('z')[0]?.textContent || 'N/A',
        });

        const route = {
            id: routeXml.getElementsByTagName('id')[0]?.textContent || 'N/A',
            name: routeXml.getElementsByTagName('name')[0]?.textContent || 'N/A',
            coordinates: {
                x: routeXml.getElementsByTagName('coordinates')[0]?.getElementsByTagName('x')[0]?.textContent || 'N/A',
                y: routeXml.getElementsByTagName('coordinates')[0]?.getElementsByTagName('y')[0]?.textContent || 'N/A',
            },
            creationDate: routeXml.getElementsByTagName('creationDate')[0]?.textContent || 'N/A',
            from: parseLocation(routeXml.getElementsByTagName('from')[0]),
            to: parseLocation(routeXml.getElementsByTagName('to')[0]),
            distance: routeXml.getElementsByTagName('distance')[0]?.textContent || 'N/A',
        };

        return route;
    }

    // Метод парсинга списка маршрутов
    private parseXmlListToJson(xmlString: string): any[] {
        const parser = new DOMParser();
        const xml = parser.parseFromString(xmlString, 'application/xml');
        const items = xml.getElementsByTagName('item');
        const routes: any[] = [];

        for (let i = 0; i < items.length; i++) {
            const item = items[i];
            const route = {
                id: item.getElementsByTagName('id')[0]?.textContent,
                name: item.getElementsByTagName('name')[0]?.textContent,
                coordinates: {
                    x: item.getElementsByTagName('coordinates')[0]?.getElementsByTagName('x')[0]?.textContent,
                    y: item.getElementsByTagName('coordinates')[0]?.getElementsByTagName('y')[0]?.textContent,
                },
                creationDate: item.getElementsByTagName('creationDate')[0]?.textContent,
                from: {
                    x: item.getElementsByTagName('from')[0]?.getElementsByTagName('x')[0]?.textContent,
                    y: item.getElementsByTagName('from')[0]?.getElementsByTagName('y')[0]?.textContent,
                    z: item.getElementsByTagName('from')[0]?.getElementsByTagName('z')[0]?.textContent,
                },
                to: {
                    x: item.getElementsByTagName('to')[0]?.getElementsByTagName('x')[0]?.textContent,
                    y: item.getElementsByTagName('to')[0]?.getElementsByTagName('y')[0]?.textContent,
                    z: item.getElementsByTagName('to')[0]?.getElementsByTagName('z')[0]?.textContent,
                },
                distance: item.getElementsByTagName('distance')[0]?.textContent,
            };
            routes.push(route);
        }

        return routes;
    }

    private parseUniqueDistances(xmlString: string): number[] {
        const parser = new DOMParser();
        const xml = parser.parseFromString(xmlString, 'application/xml');
        const items = xml.getElementsByTagName('item');
        const distances: number[] = [];

        for (let i = 0; i < items.length; i++) {
            const distanceValue = items[i].textContent;
            if (distanceValue) {
                distances.push(parseFloat(distanceValue));
            }
        }

        return distances;
    }


}
