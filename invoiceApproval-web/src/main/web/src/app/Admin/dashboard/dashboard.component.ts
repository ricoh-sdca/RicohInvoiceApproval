import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup,FormControl, Validators} from '@angular/forms';
import { FormArray } from '@angular/forms';
import { catchError, map, tap } from 'rxjs/operators';
/*Third party libraries added here */
import { NgbActiveModal, NgbModal , ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { UserService } from '../../shared/services/user.service';
import { RulesService } from '../../shared/services/rules.service';
import {Rules } from './../../shared/rules';
import { Observable  } from 'rxjs';
import {SidebarModule} from 'primeng/sidebar';
import {AccordionModule} from 'primeng/accordion';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  public show:boolean = true;
  public ruleShow:boolean = false;
  public buttonName:any = 'Show';
  public imagePath:any ;
  subDetails:boolean;
  ruleData= [];
  closeResult: string;
  rulejson:any;
  number:Number;
  createRuleobj:Rules;
  frmctrl=["true"]
  updateruleDatails;
  rowsArrLength;
  ruleDetails=[];   // for testing perpose
  currency=["US Doller"]
  popupForm:FormGroup;
  errMsg:string ;
  levelArr=[1];
  levelObj;
  count=2;
  updateArr;
  managerLevel:any;
  id;

  constructor(
    private router: Router, 
     private userService:UserService,
     private ruleService:RulesService,
     private formBulder: FormBuilder, 
     private activatedRoute:ActivatedRoute, 
     private modalService: NgbModal,
     public activeModal: NgbActiveModal ) { }

  ngOnInit() {
  /** 
   * FUNCTION   : getRules()
   * Objective  : This function is For get Rule From server
  **/
    this.ruleService.getRules()
      .subscribe(res =>{
        if(res.response.code=="SUCCESS" )
        {
          this.ruleData=res.ruleDetails,
          console.log(this.ruleData);
        }
        else
        {
          this.errMsg =res.response.errorMessage
        }                                                      
      }) ,
    this.popupForm = this.formBulder.group({
      currency:['US Doller'],
      fromAmt: ['' ,[ Validators.required]],
      toAmt: ['',Validators.required],
      mode: ['SEQ'],
      text :['', Validators.required],
      level:[''],
      updatedAt:['null' ],
  
       /** Objective  : ruleRows refer to call FormArray **/
      ruleRows: this.formBulder.array([
        this.initItemRows() 
       ]),
    });
  }
  /** 
   * FUNCTION   :  get ruleRows()
   * Objective  : This function is For get Rule array from fromArray.
  **/
  get ruleRows() {
    this.rowsArrLength=this.popupForm.get('ruleRows') as FormArray;
    return this.popupForm.get('ruleRows') as FormArray;
  }
   /** 
   * FUNCTION   : initItemRows()
   * Objective  : This function is For get Rule Field from fromArray.
  **/
  initItemRows() {
    return this.formBulder.group({
      currency:['US Doller'],
      fromAmt: ['',Validators.required],
      toAmt: [''],
      level:  [ '1' ],
      updatedAt:['null' ],
      mode:['SEQ']
    });
  }
   /** 
   * FUNCTION   : addRuleRows()
   * Objective  : This function is For Add Rule Row in fromArray.
  **/
  addRuleRows(){
    this.ruleRows.push(this.initItemRows());
    this.levelArr.push(this.count++);
    console.log( this.levelArr)
  }
     /** 
   * FUNCTION   :  deleteRuleRow()
   * Objective  : This function is For Delete Rule Row in fromArray.
  **/
  deleteRuleRow(index: number) {
    this.ruleRows.removeAt(index);
  }
  /** 
   * FUNCTION   :showSubdata()
   * Objective  : This function is For to show SubDetails of created Rule.
  **/
  showSubdata(){
    this.subDetails=true;
  }
  /** 
   * FUNCTION   :  toggle()
   * Objective  : This function is For to Toggle Invoice Summary..
  **/
  toggle() {
    this.show = !this.show;
    var toggleUp = "./assets/images/dashboard/toggleUp.png";
    var toggelDown = "./assets/images/dashboard/toggleIcon.png";
    if(this.show)  
      document.getElementById("i-summary").attributes.getNamedItem("src").value = toggelDown;
    else
      document.getElementById("i-summary").attributes.getNamedItem("src").value = toggleUp;
  }
  /** 
   * FUNCTION   : ShowSubrule()
   * Objective  : This function is For to Toggle Rule Details..
  **/
  ShowSubrule(){
    this.ruleShow = !this.ruleShow;
    var minusImgName = "./assets/images/dashboard/minus.png";
    var plusImgName = "./assets/images/dashboard/icon 13.png";
       
    if(this.ruleShow)
      document.getElementById("subRuleImgId").attributes.getNamedItem("src").value = minusImgName;
    else
      document.getElementById("subRuleImgId").attributes.getNamedItem("src").value = plusImgName;
  }
 /** 
   * FUNCTION   : resetPopForm()
   * Objective  : This function is For to Reset Popup Form.
  **/
  resetPopForm(keepFirstRow : boolean){
    var viewRuleDetails : FormArray;    
    viewRuleDetails = (this.popupForm.get("ruleRows") as FormArray);
    while(viewRuleDetails.length > 0){
    	viewRuleDetails.removeAt(0);
    }
    if(keepFirstRow){
      this.ruleRows.push(this.initItemRows());
    }
    return viewRuleDetails;
  } 
  /** 
   * FUNCTION   : createRuleData()
   * Objective  :This function is for Create Rule Modal Pop-up
  **/
  createRuleData(create){
    const modalRef = this.modalService.open(create,{ size: 'lg' });
    this.resetPopForm(true);

    modalRef.result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason,null) }`;
    });
  }
  /** 
   * FUNCTION   : viewRuleData()
   * Objective  :This function is for View Rule Modal Pop-up
  **/
  viewRuleData(view, element){
    const modalRef = this.modalService.open(view,{ size: 'lg' });
    var viewRuleDetails = this.resetPopForm(false);

    this.popupForm.get("mode").setValue(element.mode)
    viewRuleDetails = (this.popupForm.get("ruleRows") as FormArray);

    element.rule.ruleDetails.forEach(ruleDetailObj =>{
      viewRuleDetails.push(this.formBulder.group(ruleDetailObj))
      console.log(ruleDetailObj);
    })
    modalRef.result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason,element) }`;
    });
  }
  /** 
   * FUNCTION   : updateRuleData()
   * Objective  :This function is for Update Rule Modal Pop-up
  **/
  updateRuleData(update, element){
    this.id=element.id;
    const modalRef = this.modalService.open(update,{ size: 'lg' });
    var updateRuleDetails = this.resetPopForm(false);

    this.popupForm.get("mode").setValue(element.mode)
    updateRuleDetails = (this.popupForm.get("ruleRows") as FormArray);
 
    element.rule.ruleDetails.forEach(ruleDetailObj =>{
      updateRuleDetails.push(this.formBulder.group(ruleDetailObj))
    })
    modalRef.result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason,element) }`;
    });
  }
  /** 
   * FUNCTION   : deleteRuleData()
   * Objective  :This function is for Delete Rule Modal Pop-up
  **/
 deleteRuleData(remove,element){
  this.id=element.id;
    const modalRef = this.modalService.open(remove);
    this.resetPopForm(true);
    modalRef.result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason,null) }`;
    });
  }
  /** to dismiss the model pop up*/
  private getDismissReason(reason: any,element): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }
  /** 
   * FUNCTION : isRuleFiledNullOrBlank()
   * Objective : To validate Create Rule fileds for NULL / Blank
   **/ 
  isRuleFiledNullOrBlank(ruleDetailsField,tagIdVal){
    if(ruleDetailsField === null || ruleDetailsField === ''){
      document.getElementById(tagIdVal).attributes.getNamedItem("style").value = "border: 1px solid red;";
      this.errMsg = "Amount (From / To) filed cannot be blank";
      return false;
    }else if(ruleDetailsField < 0){
    	document.getElementById(tagIdVal).attributes.getNamedItem("style").value = "border: 1px solid red;";
        this.errMsg = "Amount (From / To) filed cannot have value less than zero";
        return false;
    }else{
      document.getElementById(tagIdVal).attributes.getNamedItem("style").value = "border: 1px solid #d1d1d1;";
      return true;
    }
  } 
   /** 
   * FUNCTION : validate()
   * Objective : To validate Create Rule fileds for NULL / Blank
   **/
  validate(){
	  var flag = true;
	  for(let i=0;i<this.rowsArrLength.length;i++)
	  {
      var ruleDetailsObj=this.rowsArrLength.value[i];
      // Resetting border to original here
      document.getElementById("fromAmt"+i).attributes.getNamedItem("style").value = "border: 1px solid #d1d1d1;";
      document.getElementById("toAmt"+i).attributes.getNamedItem("style").value = "border: 1px solid #d1d1d1;";
      //alert(ruleDetailsObj.fromAmt+" | "+ruleDetailsObj.toAmt+" | "+ruleDetailsObj.level +" | "+flag);
      // Validating fromAmt
      if(this.isRuleFiledNullOrBlank(ruleDetailsObj.fromAmt,"fromAmt"+i)){
    	// Validating toAmt
        if(this.isRuleFiledNullOrBlank(ruleDetailsObj.toAmt,"toAmt"+i)){
        	// Validating fromAmt >= toAmt
          if(ruleDetailsObj.fromAmt >= ruleDetailsObj.toAmt){
            flag = false;
            document.getElementById("fromAmt"+i).attributes.getNamedItem("style").value = "border: 1px solid red;";
            document.getElementById("toAmt"+i).attributes.getNamedItem("style").value = "border: 1px solid red;";
            this.errMsg = "'From Amount' cannot be greater than or equal to 'To Amount'";
            break;
          }else{flag = true;}
        }else{flag = false;}
      }else{flag = false;}
    }
    if(!flag){
      document.getElementById("errorMsgDivId").attributes.getNamedItem("style").value = "display:block;";
    }else{
      document.getElementById("errorMsgDivId").attributes.getNamedItem("style").value = "display:none;";
    }
	  return flag;
  } 
 /** 
   * FUNCTION : createRule()
   * Objective : This function is for to Create Rule.
   * **/
  createRule(){
    if(this.validate()){
      this.createRuleobj=this.popupForm.value
      this.rulejson={
        "orgId":"1", // this need to be dynamic
        "rule": {
          "type": "AmountRange",
          "ruleDetails": [ ]
        },
        "ruleStatus": "active", // to be removed
        "mode":this.popupForm.controls.mode.value,
      }
      for(let i=0;i<this.rowsArrLength.length;i++){
        this.levelObj=this.rowsArrLength.value[i];
        this.levelObj.level=[]
        this.levelObj.level.push(i+1)
        console.log(this.levelObj.level)
        var obj =this.rowsArrLength.value[i];
        this.rulejson.rule.ruleDetails.push(obj)
      }
      this.ruleService.addRule(this.rulejson)
      .subscribe(data => {
          if(data.response.code=="SUCCESS" )
          {
            /** Dismissing pop-up and reloading page to retrive Rule Details*/
            this.modalService.dismissAll("success");
            location.reload(true);
          }
          else
          {
            this.errMsg =data.response.errorMessage;
            document.getElementById("errorMsgDivId").attributes.getNamedItem("style").value = "display:block;"; 
          }
        }); 
        }else{
      console.log("Error in Rule Validation");
    }
  }
  
  /** 
   * FUNCTION   : openRuleConfPopup()
   * Objective  : Confirmation Modal Pop-up - for Update Rule
   **/
  openRuleConfPopup(ruleConfirmPopName){
    if(this.validate()){
      this.modalService.open(ruleConfirmPopName);
    }else{
      console.log("Error in Rule validation.");
    }
  }
  /** 
   * FUNCTION   : updateRule()
   * Objective  : This function is For Update Rule
  **/
  updateRule() {
      this.createRuleobj=this.popupForm.value
      this.rulejson={
        "orgId":"1", // this need to be dynamic
        "rule": {
            "type": "AmountRange",
            "ruleDetails": [ ]
            },
        "ruleStatus": "active", // to be removed
        "mode":this.popupForm.controls.mode.value,
      }
      for(let i=0;i<this.rowsArrLength.length;i++){
        this.levelObj=this.rowsArrLength.value[i];
        this.levelObj.level=[]
        this.levelObj.level.push(i+1)
        console.log(this.levelObj.level)
        var obj =this.rowsArrLength.value[i];
        this.rulejson.rule.ruleDetails.push(obj)
      }
      this.ruleService.updateRule(this.id,this.rulejson)
      .subscribe(data => {
          if(data.response.code=="SUCCESS" )
          {
            /**Dismissing pop-up and reloading page to retrive Rule Details*/
            this.modalService.dismissAll("success");
            location.reload(true);
          }
          else
          {
            this.errMsg =data.response.errorMessagep;
            document.getElementById("errorMsgDivId").attributes.getNamedItem("style").value = "display:block;"; 
          }
      });
  }
  /** 
   * FUNCTION   : deleteRule()
   * Objective  : This function is For Delete Rule
  **/
  deleteRule() {
    this.ruleService.deleteRule(this.id)
       .subscribe(data => {
          if(data.response.code=="SUCCESS" )
          {
            // Dismissing pop-up and reloading page to retrive Rule Details
            this.modalService.dismissAll("success");
            location.reload(true);
          }
          else
          {
            this.errMsg =data.response.errorMessage
          }
      }); 
  }
    /** 
   * FUNCTION   : logout()
   * Objective  : This function is For Logout redirect to login
  **/
  logout():void {
    console.log("Logout");
    this.userService.logout();
    this.router.navigate(['/login']);
  }

}
