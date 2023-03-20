import {Component} from '@angular/core';
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AuthenticationService} from "./service/authentication.service";
import {User} from "./models";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [AuthenticationService]
})
export class AppComponent {

  private closeResult: string = '';
  currentUser: User | undefined;

  constructor(private modalService: NgbModal,
              private authenticationService: AuthenticationService) {
    this.authenticationService.currentUser.subscribe(data => {
      this.currentUser = data;
    });
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

  updateUser() {
    this.currentUser = undefined;
  }
}
