import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable,  } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {
user={
  userName:"admin",
  password:"admin123"
};
res:Observable<any>;
obj={}
invoice:any;
demoUrl:string="https://jsonplaceholder.typicode.com/posts"
  constructor(private http:HttpClient) { }

getUsers(userName: string , password: string) {

  if(this.user.userName == userName && this.user.password == password) {
      sessionStorage.setItem('local currentUser', JSON.stringify(this.user))
      return this.user;
    }
    else {
      alert('unsucessful');
      return null;
    } 
  }
 
logout(): boolean {
  return true;
  }

  // demo purpose for calling facke back end .
getRecords():Observable<any>{
    console.log("hi")
    this.invoice= this.http.get(this.demoUrl)
     //console.log(this.data)
    return this.invoice
}

addRecord(){

  this.obj={
    "userId": 1,
    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "body":"hello"
  }

  console.log("helllo")
  this.res= this.http.post(this.demoUrl,this.obj)

  console.log(this.res.pipe)
}


}



