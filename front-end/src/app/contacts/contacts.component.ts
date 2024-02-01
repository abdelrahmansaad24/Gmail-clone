import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { ReadMessageComponent } from '../read-message/read-message.component';
import { delay } from 'rxjs/internal/operators/delay';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {

  contacts:any = [];
  constructor(private messageApp:ReadMessageComponent , private http:HttpClient, private myapp:AppComponent) { }
  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/contacts/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
      this.contacts = res;
    });
  }

  reload(){
    this.http.get<any[]>('http://localhost:8080/contacts/'+this.myapp.email+"/").subscribe(res => {
      this.contacts = res;
    });
  }

  openAddContactWindow(){
    this.myapp.openAddContactWindow();
  }

  ConTacts:any = new Array();
  check(email:any){
    //alert(id)
    let x = false;
    this.ConTacts.forEach( (item:any, index:any) => {
      if(item === email) {
        x = true;
        this.ConTacts.splice(index,1);
      }
    })
    if(!x){
      this.ConTacts.push(email);
    }
  }

  Delete(){
    //alert(this.message.length)
    this.ConTacts.forEach( (item:any, index:any) => {
      this.http.delete('http://localhost:8080/deletecontact/' + this.myapp.email +"/"+item+"/").subscribe();
    })

    this.http.get<any[]>('http://localhost:8080/contacts/'+this.myapp.email+"/").subscribe(res => {
      this.contacts = res;
    });
  }

}
