import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RestaurantsListComponent} from "./restaurants-list/restaurants-list.component";
import {RestaurantComponent} from "./restaurant/restaurant.component";
import {HomeComponent} from "./home/home.component";
import {RegisterComponent} from "./register/register.component";
import {LoginComponent} from "./login/login.component";
import {ReservationsComponent} from "./reservations/reservations.component";
import {AdminComponent} from "./admin/admin.component";

const routes: Routes = [
  {path: 'restaurants/:id', component: RestaurantsListComponent, runGuardsAndResolvers: 'always'},
  {path: 'restaurant/:cityName/:id', component: RestaurantComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'reservations', component: ReservationsComponent},
  {
    path: 'admin', component: AdminComponent,
    children: [
      {path: ':id', component: AdminComponent}
    ],
  },
  {
    path: '', component: HomeComponent,
    children: [
      {
        path: 'home',
        component: HomeComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
