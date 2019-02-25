import { NgModule } from '@angular/core';
import { RouterModule , Routes } from '@angular/router';

import { LoginComponent } from '../login/login.component';
import { DashboardComponent } from '../Admin/dashboard/dashboard.component';
import { AuthGuard } from '../guard/auth.guard';

const appRoutes: Routes = [
  { path: '', redirectTo:'login' , pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent , canActivate:[AuthGuard] }
];

@NgModule({
  imports: [ RouterModule.forRoot( appRoutes ) ],
  exports: [ RouterModule ],
  declarations: []
})
export class AppRoutingModule { }

