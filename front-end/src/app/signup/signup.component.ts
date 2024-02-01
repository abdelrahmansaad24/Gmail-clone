import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AppComponent } from '../app.component';
import { first, last } from 'rxjs';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  constructor(private myapp:AppComponent, private http:HttpClient) { }
  show(){

    var password= <HTMLInputElement>document.getElementById('pass1');
    let confirm = <HTMLInputElement>document.getElementById('pass2');
    let image= document.getElementById('eye');

    if (password.type==="password",confirm.type==="password") {
      password.type="text";
      confirm.type="text";
      image!.setAttribute('src', '../assets/images/eyeshow.png');

    }else if (password.type==="text",confirm.type==="text"){
      password.type="password";
      confirm.type="password";
      image!.setAttribute('src', '../assets/images/eyehide.png');
    }
  }
  submit(){
    let email:string = (<HTMLInputElement>document.getElementById("username")).value;
    const pass1:string = (<HTMLInputElement>document.getElementById("pass1")).value;
    const pass2:string = (<HTMLInputElement>document.getElementById("pass2")).value;
    const first:string = (<HTMLInputElement>document.getElementById("first")).value;
    const last:string = (<HTMLInputElement>document.getElementById("lname")).value;
    if(pass1==pass2){
      if(email!=""&&first!=""&&last!=""&&pass1!=""){
          // 
         if(email.includes("@gmail.com")){
          email = email.replace("@gmail.com","")
         } 
          const body = { 
            "userName": first.concat(" ".concat(last)), 
            "userEmail": email + "@gmail.com",
            "userPassword": pass1,
          };
          this.http.post<boolean>('http://localhost:8080/signup/', body).subscribe(data => {
            this.myapp.signup = !data;
            this.myapp.login = !data;
            this.myapp.email = body.userEmail;
          })          
      }
      else{
        alert("fill empty fields")
      }
    }
    else{
      alert("invalid confirm password")
    }
  }
  login(){
    this.myapp.signup=false;
  }

}
