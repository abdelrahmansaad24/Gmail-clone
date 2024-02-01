import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-log-in-page',
  templateUrl: './log-in-page.component.html',
  styleUrls: ['./log-in-page.component.css']
})
export class LogInPageComponent {
  constructor(private myapp:AppComponent, private http:HttpClient) { }
  signIn(){
    let email =(<HTMLInputElement>document.getElementById("email")).value;
    const password = (<HTMLInputElement>document.getElementById("password")).value;
    if(email.includes("@gmail.com"))
      email = email.replace("@gmail.com","");
    if(email != "" &&password!=""){
      const body = { 
        "userName": " ", 
        "userEmail": email + "@gmail.com",
        "userPassword": password,
      };
      //this.myapp.login=false;
      this.http.post<boolean>('http://localhost:8080/login/', body).subscribe(data => {
            this.myapp.login = !data;
            this.myapp.email=body.userEmail;
            if(!data){
              alert("invalid email or password");
            }
      })  
    }
    else{
      alert("fill empty fields")
    }
  }

  signUp(){
    this.myapp.signup = true;
  }

}
