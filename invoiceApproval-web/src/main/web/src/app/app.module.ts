import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule} from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing/app-routing.module';
import { UserService } from './shared/services/user.service';
import { AuthGuard } from './guard/auth.guard';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './Admin/dashboard/dashboard.component';
import { PendingInvoicesComponent } from './User/pending-invoices/pending-invoices.component';
import { InvoiceReportsComponent } from './Admin/invoice-reports/invoice-reports.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    PendingInvoicesComponent,
    InvoiceReportsComponent,
    
  ],

  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [UserService , AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
