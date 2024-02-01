import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-read-message',
  templateUrl: './read-message.component.html',
  styleUrls: ['./read-message.component.css']
})
export class ReadMessageComponent implements OnInit {

  constructor(private route:ActivatedRoute, private http:HttpClient,private myapp:AppComponent,) {}
  name:any;
  from:any;
  to:any;
  subject:any;
  content:any;
  time:any;
  id:number=0;
  hasAttachment: boolean = false;

  ngOnInit(): void {
    console.log("ok");
    let id = "";
    const routeParams = this.route.snapshot.paramMap;
    id = (routeParams.get('id')) == null? "" : (routeParams.get('id')!);
    this.name = (routeParams.get('from'))?.split('@')[0];//(routeParams.get('name'));
    this.from = (routeParams.get('from'));
    this.to = (routeParams.get('to'));
    this.subject = (routeParams.get('subject'));
    this.content = (routeParams.get('content'));
    this.time = (routeParams.get('time'));
    this.id = +id;
    this.hasAttachment = (routeParams.get('hasAttachment')) == 'true';
    this.setRead();

  }

  download(){
    this.myapp.download(this.id);
  }

  setRead(){

    //id of message should sent to back end to set read = true

  }

}
