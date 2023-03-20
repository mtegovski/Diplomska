import { Component, OnInit } from '@angular/core';
import {RestService} from "../service/rest.service";
import {Reservation, Restaurant} from "../models";
import {ActivatedRoute} from "@angular/router";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AuthenticationService} from "../service/authentication.service";

@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.css']
})
export class RestaurantComponent implements OnInit {

  // @ts-ignore
  restaurant: Restaurant;
  restaurantImages: Array<string> = [];
  numberOfGuests: number | undefined;
  date: Date | undefined;
  private closeResult: string = '';
  constructor(private restService: RestService,
              private route: ActivatedRoute,
              private modalService: NgbModal,
              private authService: AuthenticationService) {
  }

  ngOnInit(): void {
    const id: number = this.route.snapshot.params['id'];
    const cityName: string = this.route.snapshot.params['cityName'];
    this.restService.getRestaurantByID(id).subscribe(data => {
      this.restaurant = data;
      for (let i = 1; i<=this.restaurant.numberOfPhotos; i++) {
        this.restaurantImages.push('/assets/photos/' +
          cityName.toLowerCase() + '/'
          + this.restaurant.name.toLowerCase() + '/' + i + '.jpg');
      }
    });
  }

  getImageClass(index: number): string {
    if (index === this.restaurantImages.length -1 && index % 2 === 1) {
      return 'wide';
    } else {
      return 'big';
    }
  }

  openModal(content: any): void {
    this.modalService.open(content,
      {
        ariaLabelledBy: 'modal-basic-title'
      }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  isDateInPast(): boolean {
    // @ts-ignore
    const ourDate = new Date(this.date);
    return ourDate.getTime() < Date.now();
  }

  makeReservation(modal: any) {
    if (!this.isDateInPast()) {
      const reservation = {
        numberOfGuests: this.numberOfGuests,
        restaurantId: this.restaurant.id,
        restaurantName: this.restaurant.name,
        email: this.authService.currentUserValue.email,
        date: this.date
      } as Reservation;
      this.restService.makeReservation(reservation).subscribe(() => {});
      modal.close();
    }
  }
}
