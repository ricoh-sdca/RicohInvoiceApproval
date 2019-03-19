import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable,  } from 'rxjs';
import {environment} from './../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
user={
  userName:"admin",
  password:"admin123"
};

data:any;
res:Observable<any>;
obj:any;
invoice:any;
baseUrl=environment.baseUrl;

constructor(private http:HttpClient) { }

getUsers(username:string,password:string):Observable<any> {
 
  this.obj={
    "username":username,
    "password":password
  }
  
  this.data= this.http.post(this.baseUrl+'/login',this.obj)
  console.log(this.data)
  return this.data
  }
 
logout(): boolean {
  return true;
  }
}



