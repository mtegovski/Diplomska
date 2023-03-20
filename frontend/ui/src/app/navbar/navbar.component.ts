import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {City, Role, User} from "../models";
import {RestService} from "../service/rest.service";
import {Router} from "@angular/router";
import {map} from "../const-variables"
import {AuthenticationService} from "../service/authentication.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  @Input() currentUser: User | undefined;
  @Output() contactModal = new EventEmitter<void>();
  @Output() aboutUsModal = new EventEmitter<void>();
  @Output() signOutPerson = new EventEmitter<void>();
  cities: Array<City> = new Array<City>();

  constructor(private restService: RestService,
              private router: Router,
              private authenticationService: AuthenticationService) {}

  ngOnInit(): void {
    this.restService.getAllCitiesWithRestaurants().then(data => {
      this.cities = data;
    });
  }

  redirectToCity(id: number) {
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(()=>
      this.router.navigate(['restaurants/' + id]));
  }

  getCityCirylicName(name: string) {
    return map.get(name);
  }

  openContactModal(): void {
    this.contactModal.emit();
  }
  openAboutUsModal(): void {
    this.aboutUsModal.emit();
  }

  signOut() {
    this.authenticationService.logOut();
    this.router.navigate(['/login']).then(() => this.ngOnInit());
    this.signOutPerson.emit();
  }

  isUserAdmin(): boolean {
    return this.currentUser?.role === Role.ADMIN;
  }
}
