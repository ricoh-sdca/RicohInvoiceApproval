import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../shared/services/user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor( private router : Router , private userService:UserService){}
  
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
     //var currentUser= this.userService.getUsers
    
    if(sessionStorage.getItem('local currentUser')!=null) {
      console.log(sessionStorage.getItem('local currentUser'))
      return true
    }
    else{

      return false
    }
  
    }  
    
  }

