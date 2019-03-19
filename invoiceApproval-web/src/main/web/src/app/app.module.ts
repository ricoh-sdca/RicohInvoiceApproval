import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule} from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing/app-routing.module';
import { UserService } from './shared/services/user.service';
import { RulesService } from './shared/services/rules.service';
import { PendingInvoiceService } from './shared/services/pending-invoice.service';

import { AuthGuard } from './guard/auth.guard';
import { NgbModule, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import {SidebarModule} from 'primeng/sidebar';
import {AccordionModule} from 'primeng/accordion';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './Admin/dashboard/dashboard.component';
import { PendingInvoicesComponent } from './User/pending-invoices/pending-invoices.component';
import { UserComponent } from './user/user.component';
import { InvoiceReportsComponent } from './admin/invoice-reports/invoice-reports.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    PendingInvoicesComponent,
    UserComponent,
    InvoiceReportsComponent,
  ],

  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule, 
    ReactiveFormsModule,
    AppRoutingModule,
    NgbModule.forRoot(),
    
     //third-party PRIMENG
    SidebarModule,
    AccordionModule,
    BrowserAnimationsModule
  ],
  providers: [UserService , RulesService ,PendingInvoiceService, AuthGuard, NgbActiveModal ],
  bootstrap: [AppComponent]
})
export class AppModule { }
