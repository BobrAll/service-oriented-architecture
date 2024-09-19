import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs'
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private baseUrl = 'https://localhost:8443/api/v1/locations';

  constructor(private http: HttpClient) {}

  getLocationById(id: number): Observable<any> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get(url, { responseType: 'text' }).pipe(
        map((xmlString: string) => this.parseXmlToJson(xmlString))
    );
  }

  getAllLocations(): Observable<any[]> {
    return this.http.get(this.baseUrl, { responseType: 'text' }).pipe(
      map((xmlString: string) => this.parseXmlListToJson(xmlString))
    );
  }

  updateLocation(id: number, location: any): Observable<any> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.put(url, location, { responseType: 'text' })
        .pipe(map((xmlString: string) => this.parseXmlToJson(xmlString)));
  }

  addLocation(location: any): Observable<any> {
    return this.http.post(this.baseUrl, location, { responseType: 'text' })
        .pipe(map((xmlString: string) => this.parseXmlToJson(xmlString)));
  }

  deleteLocation(id: number): Observable<any> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete(url, { responseType: 'text' })
        .pipe(map((xmlString: string) => this.parseXmlToJson(xmlString)));
  }


  private parseXmlToJson(xmlString: string): any {
    const parser = new DOMParser();
    const xml = parser.parseFromString(xmlString, 'application/xml');
    const item = xml.getElementsByTagName('item')[0];
    if (!item) return null;

    const location = {
      id: item.getElementsByTagName('id')[0]?.textContent,
      x: item.getElementsByTagName('x')[0]?.textContent,
      y: item.getElementsByTagName('y')[0]?.textContent,
      z: item.getElementsByTagName('z')[0]?.textContent,
    };

    return location;
  }

  private parseXmlListToJson(xmlString: string): any[] {
    const parser = new DOMParser();
    const xml = parser.parseFromString(xmlString, 'application/xml');
    const items = xml.getElementsByTagName('item');
    const locations: any[] = [];

    for (let i = 0; i < items.length; i++) {
      const item = items[i];
      const location = {
        id: item.getElementsByTagName('id')[0]?.textContent,
        x: item.getElementsByTagName('x')[0]?.textContent,
        y: item.getElementsByTagName('y')[0]?.textContent,
        z: item.getElementsByTagName('z')[0]?.textContent,
      };
      locations.push(location);
    }

    return locations;
  }
}
