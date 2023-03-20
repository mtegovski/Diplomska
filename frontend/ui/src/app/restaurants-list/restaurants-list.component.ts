import {Component, OnInit} from '@angular/core';
import {RestService} from "../service/rest.service";
import {City, Restaurant} from "../models";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-restaurants-list',
  templateUrl: './restaurants-list.component.html',
  styleUrls: ['./restaurants-list.component.css']
})
export class RestaurantsListComponent implements OnInit {
  city: City = {} as City;
  restaurants: Array<Restaurant> = [];

  constructor(private restService: RestService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const cityId = this.route.snapshot.params['id'];
    this.restService.getAllRestaurantsInCity(cityId).subscribe(data => {
      this.city = data;
      this.restaurants = this.city.restaurants;
    });
  }

  filterRestaurants(category: string): void {
    if (category === '') {
      this.restaurants = this.city.restaurants;
    } else {
      this.restaurants = this.city.restaurants.filter(r => r.restaurantCategory.toLowerCase() === category.toLowerCase());
    }
  }

  filterBySearch(searchText: string) {
    if (searchText.length !== 0) {
      this.restaurants = this.city.restaurants;
      this.restaurants = this.restaurants.filter(r => r.name.toLowerCase().includes(searchText.toLowerCase()))
    } else {
      this.restaurants = this.city.restaurants;
    }
  }

  refresh() {
    const cityId = this.route.snapshot.params['id'];
    this.restService.getAllRestaurantsInCity(cityId).subscribe(data => {
      this.city = data;
      this.restaurants = this.city.restaurants;
    });
  }
}
