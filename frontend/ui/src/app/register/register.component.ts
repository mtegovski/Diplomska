import {Component, OnInit} from '@angular/core';
import {User} from "../models";
import {Router} from "@angular/router";
import {AuthenticationService} from "../service/authentication.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = {} as User;
  errorMessage: string = '';

  constructor(private authenticationService: AuthenticationService, private router: Router) {
  }

  ngOnInit(): void {
    if (this.authenticationService.currentUserValue?.email) {
      this.router.navigate(['']);
    }
  }

  register() {
    this.authenticationService.register(this.user).subscribe(() => {
      this.router.navigate(['/login']);
    });
  }

}
