import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RestaurantsListComponent } from './restaurants-list/restaurants-list.component';
import {HttpClientModule} from "@angular/common/http";
import { RestaurantComponent } from './restaurant/restaurant.component';
import { RestaurantCardComponent } from './restaurant-card/restaurant-card.component';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { CityHeaderComponent } from './city-header/city-header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { ReservationsComponent } from './reservations/reservations.component';
import { AdminComponent } from './admin/admin.component';
import {RecaptchaModule} from "ng-recaptcha";

@NgModule({
  declarations: [
    AppComponent,
    RestaurantsListComponent,
    RestaurantComponent,
    RestaurantCardComponent,
    NavbarComponent,
    HomeComponent,
    CityHeaderComponent,
    RegisterComponent,
    LoginComponent,
    ReservationsComponent,
    AdminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    RecaptchaModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
