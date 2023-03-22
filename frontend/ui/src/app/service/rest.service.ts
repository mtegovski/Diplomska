import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {City, Reservation, Restaurant} from "../models";
import {firstValueFrom, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RestService {

  baseURL = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getRestaurantByID(id: number): Observable<Restaurant> {
    return this.http.get<Restaurant>(this.baseURL + '/restaurant/' + id);
  }

  getAllCities(): Observable<Array<City>> {
    return this.http.get<Array<City>>(this.baseURL + '/city');
  }

  getAllRestaurantsInCity(id: number): Observable<City> {
    return this.http.get<City>(this.baseURL + '/city/' + id);
  }
  async getAllCitiesWithRestaurants(): Promise<Array<City>> {
    return firstValueFrom(this.http.get<Array<City>>(this.baseURL + '/city'));
  }

  makeReservation(reservation: Reservation): Observable<Reservation> {
    return this.http.post<Reservation>(this.baseURL + '/reservation', reservation);
  }

  editReservation(reservation: Reservation, id: number): Observable<Reservation> {
    return this.http.put<Reservation>(this.baseURL + '/reservation/' + id, reservation);
  }

  deleteReservation(id: number): Observable<void> {
    return this.http.delete<void>(this.baseURL + '/reservation/' + id);
  }

  addRestaurant(restaurant: Restaurant): Observable<Restaurant>{
    return this.http.post<Restaurant>(this.baseURL + '/restaurant', restaurant);
  }
  editRestaurant(restaurant: Restaurant, id: number): Observable<Restaurant> {
    return this.http.put<Restaurant>(this.baseURL + '/restaurant/' + id, restaurant);
  }

  deleteRestaurant(id: number): Observable<void> {
    return this.http.delete<void>(this.baseURL + '/restaurant/' + id);
  }

  getReservationsForUser(email: string | undefined): Observable<Array<Reservation>>{
    return this.http.get<Array<Reservation>>(this.baseURL + '/reservation/user?email=' + email);
  }
}
