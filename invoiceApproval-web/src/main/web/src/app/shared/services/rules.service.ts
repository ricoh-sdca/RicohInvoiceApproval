import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable  } from 'rxjs';
import {Rules } from './../../shared/rules';
import {environment} from './../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RulesService {
  rulesArray:Observable<any>;
  rule:any;
  public count = 0;
  baseUrl=environment.baseUrl;
  ruledata: any;

  constructor( private http:HttpClient) { }
  
  getRules(){
    this.rulesArray=this.http.get<Rules>(this.baseUrl+'/rules/orgId/1')
    console.log(this.rulesArray)
    return this.rulesArray
  }
  
  addRule(obj):Observable<Rules>{
    return this.http.post<Rules>(this.baseUrl+'/rules' , obj)
  }

  updateRule(id:number,obj) {
 
   return this.http.put<Rules>(this.baseUrl+'/rules/update/'+id , obj)
  }

   deleteRule(id:number){
    return this.http.delete<Rules>(this.baseUrl+'/rules/delete/'+id)
   }

}