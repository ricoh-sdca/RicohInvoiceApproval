import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable  } from 'rxjs';
import {PendingInvoice} from './../../shared/pendingInvoice';
import {InvoiceAction} from './../../shared/pendingInvoice';
import {environment} from './../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PendingInvoiceService {
  baseUrl=environment.baseUrl;
  data:any;
  constructor(private http:HttpClient) { }

  getPendingInvoices(){
    return this.http.get<PendingInvoice>(this.baseUrl+'/invoices/user/pending');
  }

  approveInvoice(invoiceObj){    
    this.data = this.http.post<InvoiceAction>(this.baseUrl+'/invoices/approve',invoiceObj);
    return this.data;
  }

  rejectInvoice(invoiceObj){
    this.data = this.http.post<InvoiceAction>(this.baseUrl+'/invoices/reject',invoiceObj);
    return this.data;
  }
  
  logout(): boolean {
	  return true;
  }
}