import { Injectable } from '@angular/core';
import {BehaviorSubject, map, Observable} from "rxjs";
import {User} from "../models";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  baseURL = 'http://localhost:8080/api';
  public currentUser: Observable<User>;
  private currentUserSubject: BehaviorSubject<User>;

  constructor(private http: HttpClient) {
    let storageUser;
    const storageUserAsStr = localStorage.getItem('currentUser');
    if (storageUserAsStr) {
      storageUser = JSON.parse(storageUserAsStr);
    }

    this.currentUserSubject = new BehaviorSubject<User>(storageUser);
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(user: User): Observable<any> {
    return this.http.post<User>(this.baseURL + '/authentication/sign-in', user).pipe(
      map(reponse => {
        if (reponse) {
          this.setSessionUser(reponse);
        }
        return reponse;
      })
    )
  }

  setSessionUser(user: User) {
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }

  register(user: User): Observable<any> {
    return this.http.post<User>(this.baseURL + '/authentication/sign-up', user);
  }

  logOut() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next({} as User);
  }

  refreshToken(): Observable<any>{
    return this.http.post<User>(this.baseURL + '/authentication/refresh-token?token=' + this.currentUserValue?.refreshToken, {});
  }
}
