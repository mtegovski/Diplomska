import {Component, OnInit} from '@angular/core';
import {RestService} from "../service/rest.service";
import {City, Restaurant} from "../models";
import {map} from "../const-variables";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  readonly categories = ['Bar', 'Restaurant', 'Coffee bar', 'Night club'];
  // @ts-ignore
  cities: Array<City>;
  // @ts-ignore
  form: FormGroup;
  // @ts-ignore
  restaurant: Restaurant;
  isEditingCity = false;

  constructor(private restService: RestService, private route: ActivatedRoute, private formBuilder: FormBuilder
    , private router: Router) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      restaurantName: new FormControl(null, Validators.required),
      restaurantCategory: new FormControl('Bar', Validators.required),
      restaurantRating: new FormControl(null, [Validators.required, Validators.min(1), Validators.max(5)]),
      restaurantPhotos: new FormControl(null, [Validators.required, Validators.min(1), Validators.max(20)]),
      restaurantCity: new FormControl(1, Validators.required),
      restaurantCapacity: new FormControl(null, [Validators.required, Validators.min(10)])
    });
    const restaurantId: number = this.route.firstChild?.snapshot.params['id'];
    this.restService.getAllCities().subscribe((data: Array<City>) => {
      this.cities = data;
      if (restaurantId !== null && restaurantId !== undefined) {
        this.form.get('restaurantCity')?.setValue(this.getCityIdOfRestaurant(data));
      }
    });
    if (restaurantId !== null && restaurantId !== undefined) {
      this.isEditingCity = true;
      this.restService.getRestaurantByID(restaurantId).subscribe((data: Restaurant) => {
        this.restaurant = data;
        this.form.get('restaurantName')?.setValue(this.restaurant.name);
        this.form.get('restaurantRating')?.setValue(this.restaurant.rating);
        this.form.get('restaurantPhotos')?.setValue(this.restaurant.numberOfPhotos);
        this.form.get('restaurantCapacity')?.setValue(this.restaurant.maxCapacity);
      });
    }
  }

  shouldBeSelectedCity(id: number): boolean {
    return this.restaurant !== null && this.restaurant !== undefined && id === this.getCityIdOfRestaurant(this.cities);
  }

  shouldBeSelectedCategory(c: string): boolean {
    const restaurantId: number = this.route.firstChild?.snapshot.params['id'];
    if (restaurantId === null || restaurantId === undefined) {
      return false;
    }
    return this.restaurant.restaurantCategory.toLowerCase() === c.toLowerCase();
  }

  getCityIdOfRestaurant(data: Array<City>): number {
    let cityId: number = -1;
    data.forEach(city => {
      city.restaurants.forEach(r => {
        if (r.id === this.restaurant.id) {
          cityId = city.id;
        }
      })
    });
    return cityId;
  }

  getCirylicCityName(city: string) {
    return map.get(city);
  }

  addRestaurant() {
    const restaurantId: number = this.route.firstChild?.snapshot.params['id'];
    const restaurant = {
      name: this.form.get('restaurantName')?.value,
      restaurantCategory: this.form.get('restaurantCategory')?.value,
      rating: this.form.get('restaurantRating')?.value,
      numberOfPhotos: this.form.get('restaurantPhotos')?.value,
      cityId: +this.form.get('restaurantCity')?.value,
      maxCapacity: this.form.get('restaurantCapacity')?.value
    } as Restaurant;
    if (restaurantId !== null && restaurantId !== undefined) {
      this.restService.editRestaurant(restaurant, restaurantId).subscribe((data) => {
        console.log('Submited PUT data: ' + data);
      });
    } else {
      this.restService.addRestaurant(restaurant).subscribe((data) => {
        console.log('Submited POST data: ' + data);
      });
    }
    this.router.navigate(['']);
  }

}
