import {Component, OnInit} from '@angular/core';
import {User} from "../models";
import {AuthenticationService} from "../service/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = {} as User;
  errorMessage: string = '';
  captcha: string | undefined;

  constructor(private authenticationService: AuthenticationService, private router: Router) {
  }

  ngOnInit(): void {
    if (this.authenticationService.currentUserValue?.email) {
      this.router.navigate(['']);
    }
  }

  resolved(captchaResponse: string) {
    this.captcha = captchaResponse;
  }

  login() {
    if (this.captcha && this.captcha !== '' && this.captcha.length > 0) {
      this.authenticationService.login(this.user).subscribe(() => {
        this.router.navigate(['']);
      });
    }
  }
}
