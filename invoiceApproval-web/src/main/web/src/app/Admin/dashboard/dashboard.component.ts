import { Component, OnInit } from '@angular/core';
import {  ActivatedRoute, Router } from '@angular/router';

import { UserService } from '../../shared/services/user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
 //data:Array<any> = []
 invoice:Array<any>=[]

  constructor(private router: Router,  private userService:UserService) { }

  ngOnInit() {}

  logout():void {
     console.log("Logout");
    this.userService.logout();
    this.router.navigate(['/login']);
    }

  getRecords(){
    console.log("hello")
    this.userService.getRecords()
          .subscribe( res =>{ this.invoice=res,console.log(this.invoice)})
            }

  addRecords(){
    this.userService.addRecord()
             }            
}

