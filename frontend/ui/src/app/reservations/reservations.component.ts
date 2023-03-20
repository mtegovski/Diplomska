import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../service/authentication.service";
import {Reservation, User} from "../models";
import {RestService} from "../service/rest.service";
import {Router} from "@angular/router";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {formatDate} from "@angular/common";

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit {

  private closeResult: string = '';
  currentUser: User | undefined;
  // @ts-ignore
  reservations: Array<Reservation>;

  constructor(private authService: AuthenticationService, private restService: RestService, private router: Router,
              private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.currentUser = this.authService.currentUserValue;
    this.restService.getReservationsForUser(this.currentUser?.email).subscribe(data => {
      this.reservations = data;
    });
  }

  editReservation(modal: any, reservation: Reservation, id: number) {
    if (!this.isDateInPast()) {
      const r = {
        numberOfGuests: reservation.numberOfGuests,
        restaurantId: reservation.restaurantId,
        email: this.authService.currentUserValue.email,
        date: reservation.date,
        id: id
      } as Reservation;
      this.restService.editReservation(r, id).subscribe(() => {
      });
      modal.close();
    }
  }

  deleteReservation(id: number) {
    this.restService.deleteReservation(id).subscribe(() => {
    });
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
      this.router.navigate(['reservations']));
    this.restService.getReservationsForUser(this.currentUser?.email).subscribe(data => {
      this.reservations = data;
    });
  }

  isDateInPast(): boolean {
    // @ts-ignore
    const ourDate = new Date(this.date);
    return ourDate.getTime() < Date.now();
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
}
