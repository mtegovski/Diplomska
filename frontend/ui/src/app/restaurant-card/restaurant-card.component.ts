import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Restaurant, Role} from "../models";
import {Router} from "@angular/router";
import {AuthenticationService} from "../service/authentication.service";
import {RestService} from "../service/rest.service";

@Component({
  selector: 'app-restaurant-card',
  templateUrl: './restaurant-card.component.html',
  styleUrls: ['./restaurant-card.component.css']
})
export class RestaurantCardComponent implements OnInit {

  // @ts-ignore
  @Input() restaurant: Restaurant;
  // @ts-ignore
  @Input() cityName: string;
  @Output() refreshRestaurants = new EventEmitter<void>();

  constructor(private router: Router, private authService: AuthenticationService, private restService: RestService) {
  }

  ngOnInit() {
    this.restaurant.currentFreeCapacity = this.restaurant.maxCapacity;
    this.restaurant.reservations
      .filter(r => new Date(r.date).setHours(0, 0, 0, 0) == new Date().setHours(0, 0, 0, 0) &&
        new Date(r.date).getHours() >= new Date().getHours())
      .forEach(r => this.restaurant.currentFreeCapacity -= +r.numberOfGuests);
    setTimeout(() => {
    }, 1500);
  }

  getSelectedCityFirstRestaurantImage(restaurantName: string): string {
    return '/assets/photos/' + this.cityName.toLowerCase() + '/' + restaurantName.toLowerCase() + '/1.jpg';
  }

  openRestaurant() {
    this.router.navigate(['restaurant/' + this.cityName.toLowerCase() + '/' + this.restaurant.id])
  }

  isAdminUser(): boolean {
    return this.authService.currentUserValue.role === Role.ADMIN;
  }

  deleteRestaurant() {
    this.restService.deleteRestaurant(this.restaurant.id).subscribe(() => {
    });
    this.refreshRestaurants.emit();
  }
}
