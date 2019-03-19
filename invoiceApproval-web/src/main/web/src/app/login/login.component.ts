import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { UserService } from '../shared/services/user.service';
import { Observable } from '../../../node_modules/rxjs';

export class Data{
  errorMessage: string
  message: string
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  loginForm: FormGroup;
  submitted = false;
  returnUrl: string;
  message: string='';
  error = '';
  data:Data;
  
constructor(
      private formBuilder: FormBuilder,
      private activatedRoute:ActivatedRoute, 
      private router:Router ,
      private  userService:UserService) {}

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
       userName: ['', Validators.required ],
       password: ['', Validators.required ]
    }); 
  }
// convenience getter for easy access to form fields

  get f() { 
        return this.loginForm.controls; 
  }
  
  onSubmit() {
    this.submitted= true;
    if (this.loginForm.invalid) {
          alert("not validate");
        }
    else
    {
      this.userService.getUsers(this.f.userName.value , 
        this.f.password.value).subscribe(res =>{ 
                                      this.data=res,
                                      this.data.message
        if(this.data.message=="Login Successful."){
           
          var admin=this.f.userName.value;
          if(admin.includes("admin")){
            this.router.navigate(['/dashboard']); 
           }
          else{
            this.router.navigate(['/pendingInvoices']); 
          }
        }
        else{
            this.message = "Please enter valid  username and password"; 
        }
      })
     }
    }
  }

