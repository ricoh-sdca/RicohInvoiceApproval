import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { UserService } from '../shared/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;
  returnUrl: string;
  message: string;
  error = '';
  
  constructor(
      private formBuilder: FormBuilder,
      private activatedRoute:ActivatedRoute, 
      private router:Router ,
      private  userService:UserService) {}

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
       userName: ['', Validators.required ],
       password: ['', [Validators.required, Validators.minLength(6)]]
    }); 
  }

// convenience getter for easy access to form fields

  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted= true;
    if (this.loginForm.invalid) {
          alert("unsucessful");
        }
    else
    {
    if(this.userService.getUsers(this.f.userName.value , this.f.password.value)){
      alert("Login successful");
      this.router.navigate(['/dashboard']); // when authentication get successful then navigate to Dashboard.
    }
    else{
      this.message = "Please enter valid  username and password";  
     }  
    }
  }
}
