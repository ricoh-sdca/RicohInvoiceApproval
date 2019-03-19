import { Component, OnInit } from '@angular/core';
import { PendingInvoiceService } from '../../shared/services/pending-invoice.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

import { Observable  } from 'rxjs';

@Component({
  selector: 'app-pending-invoices',
  templateUrl: './pending-invoices.component.html',
  styleUrls: ['./pending-invoices.component.css']
})
export class PendingInvoicesComponent implements OnInit {
  PendingInvoices=[] ;
  data:any;
  errMsg;
  closeResult: string;
  invoiceJson: any;
  currApproveInvNum: string;
  currRejectInvNum: string;
  constructor( private pendingInvoiceService: PendingInvoiceService,
    private router: Router, 
    private activatedRoute:ActivatedRoute,
    private modalService: NgbModal) { }

  ngOnInit() {
    // Pending invoice data is received here from server
    this.pendingInvoiceService.getPendingInvoices()
                                .subscribe(res=>{
                                        if(res.response.code=="SUCCESS" )
                                        {
                                            this.PendingInvoices = res.invoiceDetails;
                                        }
                                        else
                                        {
                                            this.errMsg =res.response.errorMessage;
                                        }
                                        console.log(res.invoiceDetails)
      });                                        
  }
  
  // This function is for to open View Modal Pop-up
  invoiceViewData(invoiceView) {
    this.modalService.open(invoiceView, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }
  
   // This function is for to open Approve Modal Pop-up
   approveInvoiceData(approve,invoiceNumber) {
    this.currApproveInvNum = invoiceNumber;
    this.modalService.open(approve, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
    this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  // This function is for to open reject Modal Pop-up
  rejectInvoiceData(reject,invoiceNumber) {
  this.currRejectInvNum = invoiceNumber;
  this.modalService.open(reject, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
  this.closeResult = `Closed with: ${result}`;
       }, (reason) => {
    this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  // this function is for to close modal popup
  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }

  /* This method is used for APPROVE INVOICE */
  approveInvoice()
  {
    var invoiceComments = "";//document.getElementById("approveTextAreaId").attributes.getNamedItem("approveTextAreaId").value;
    this.invoiceJson={
      "username":"user", // TO BE REMOVED LATER
      "invoiceNumber":this.currApproveInvNum,
      "comments":invoiceComments
    }
    console.log("this.currApproveInvNum >> "+this.invoiceJson.invoiceNumber);

    this.pendingInvoiceService.approveInvoice(this.invoiceJson).subscribe(data => {
          if(data.response.code=="SUCCESS" )
          {
            // Dismissing pop-up and reloading page
            this.modalService.dismissAll("success");
            location.reload(true);
          }else{
            this.errMsg =data.response.errorMessage
          }
    }); 
  }  
  
  /* This method is used for REJECT INVOICE */
  rejectInvoice()
  {
    var invoiceComments = "";//document.getElementById("approveTextAreaId").attributes.getNamedItem("approveTextAreaId").value;
    this.invoiceJson={
      "username":"user", // TO BE REMOVED LATER
      "invoiceNumber":this.currRejectInvNum,
      "comments":invoiceComments
    }
    console.log("this.currRejectInvNum >> "+this.currRejectInvNum);
    this.pendingInvoiceService.rejectInvoice(this.invoiceJson).subscribe(data => {
      if(data.response.code=="SUCCESS" )
      {
        // Dismissing pop-up and reloading page
        this.modalService.dismissAll("success");
        location.reload(true);
      }else{
        this.errMsg =data.response.errorMessage
      }
});
  }

  /** 
   * FUNCTION   : logout()
   * Objective  : This function is For Logout redirect to login
  **/
  logout():void {
    console.log("Logout");
    this.pendingInvoiceService.logout();
    this.router.navigate(['/login']);
  }

}