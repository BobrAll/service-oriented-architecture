import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { map, Observable, Subject, tap } from 'rxjs'

@Injectable({
    providedIn: 'root'
})
export class NavigatorService {
    private baseUrl = 'https://localhost:9908/api/v1/navigator/route';
    private _refreshNeeded$ = new Subject<void>();

    get refreshNeeded$() {
        return this._refreshNeeded$;
    }
    constructor(private http: HttpClient) { }

    findShortestRoute(fromId: string, toId: string): Observable<any> {
        const url = `${this.baseUrl}/${fromId}/${toId}/shortest`;
        return this.http.get(url, { responseType: 'text' })
            .pipe(
                map((xmlString: string) => this.parseXmlToJson(xmlString))
            );
    }

    addRoute(fromId: number, toId: number, distance: number, routeRequest: any): Observable<any> {
        const url = `${this.baseUrl}/add/${fromId}/${toId}/${distance}`;

        const body = {
            routeName: routeRequest.name || 'No Name',
            coordinates: {
                x: routeRequest.coordinates?.x || 0,
                y: routeRequest.coordinates?.y || 0
            }
        };

        return this.http.post(url, body, {
            headers: { 'Content-Type': 'application/json' },
            responseType: 'text'
        })
            .pipe(
                map((xmlString: string) => {
                    console.log('Received XML:', xmlString); // Выводим весь ответ в консоль
                    return this.parseXmlItemToJson(xmlString);
                }),
                tap(() => {
                    this._refreshNeeded$.next();
                })
            );
    }

    private parseXmlToJson(xmlString: string): any {
        const parser = new DOMParser();
        const xml = parser.parseFromString(xmlString, 'application/xml');
        const route = xml.getElementsByTagName('item')[0]; // Изменяем 'Route' на 'item', так как в XML они находятся внутри 'List'

        if (!route) {
            throw new Error('Route not found in XML');
        }

        return {
            id: route.getElementsByTagName('id')[0]?.textContent,
            name: route.getElementsByTagName('name')[0]?.textContent,
            distance: route.getElementsByTagName('distance')[0]?.textContent,
            coordinates: {
                x: route.getElementsByTagName('coordinates')[0]?.getElementsByTagName('x')[0]?.textContent,
                y: route.getElementsByTagName('coordinates')[0]?.getElementsByTagName('y')[0]?.textContent
            },
            from: {
                x: route.getElementsByTagName('from')[0]?.getElementsByTagName('x')[0]?.textContent,
                y: route.getElementsByTagName('from')[0]?.getElementsByTagName('y')[0]?.textContent,
                z: route.getElementsByTagName('from')[0]?.getElementsByTagName('z')[0]?.textContent
            },
            to: {
                x: route.getElementsByTagName('to')[0]?.getElementsByTagName('x')[0]?.textContent,
                y: route.getElementsByTagName('to')[0]?.getElementsByTagName('y')[0]?.textContent,
                z: route.getElementsByTagName('to')[0]?.getElementsByTagName('z')[0]?.textContent
            },
            creationDate: route.getElementsByTagName('creationDate')[0]?.textContent
        };
    }

    private parseXmlItemToJson(xmlString: string): any {
        const parser = new DOMParser();
        const xml = parser.parseFromString(xmlString, 'application/xml');
        const routeXml = xml.getElementsByTagName('Route')[0]; // Мы ожидаем, что это один Route

        if (!routeXml) return null;

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
}