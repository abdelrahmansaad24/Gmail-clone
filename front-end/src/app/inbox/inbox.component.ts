import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input, NgModule, OnInit, Output, } from '@angular/core';
import { AppComponent } from '../app.component';
import { ReadMessageComponent } from '../read-message/read-message.component';
import { FormControl, FormGroup } from '@angular/forms';
import { AppService } from '../app.service';
import { catchError } from 'rxjs';
import { delay } from 'rxjs/internal/operators/delay';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css']
})

export class InboxComponent implements OnInit {
  emails:any = [];
  @Input() keySearch = '';
  selectedFolders: string[] = [];
  element = document.getElementById("simSearch");
  constructor(private messageApp:ReadMessageComponent, private  appService: AppService , private http:HttpClient,private myapp:AppComponent) { }
  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/inbox/'+ this.myapp.email + "/").pipe(delay(500)).subscribe(res => {
      this.emails = res;
    });
  }
  Search(){
    if (this.myapp.openSearchFrom){
      this.myapp.openSearchFrom = false;
      const body = this.myapp.allSearch();
      this.http.post<any[]>('http://localhost:8080/allSearch/inbox/' +  this.myapp.email +"/", body).pipe(delay(500)).subscribe(data => {

            if(!data){
              alert("error");
            }else{
              this.emails = data;
              //this.WriteMessage = false;
              this.myapp.searchFrom = new FormGroup({
                from: new FormControl(null),
                to: new FormControl(null),
                subject: new FormControl(null),
                body: new FormControl(null),
                date: new FormControl(null),
                attach:new FormControl(null)
              });
            }
      })
    }else{
      const searchKey = (<HTMLInputElement>document.getElementById("search")).value
      if(searchKey == null || searchKey == ''){
        return;
      }
      this.http.get<any[]>('http://localhost:8080/search/inbox/'+ this.myapp.email + "/" + searchKey +"/").pipe(delay(500)).subscribe(res => {
        this.emails = res;
      });
    }
  }
  click_b(){
    console.log("nnnnnn");
  }
  reload(){
    this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
      this.emails = res;
    });
  }
  open(id:any){
    this.http.get<any[]>('http://localhost:8080/makingread/inbox/'+this.myapp.email+"/" + id +"/").pipe(delay(500)).subscribe();
    //attachment
  }
  message:any = new Array();
  check(id:any){
    //alert(id)
    let x = false;
    this.message.forEach( (item:any, index:any) => {
      if(item === id) {
        x = true;
        this.message.splice(index,1);
      }
    })
    if(!x){
      this.message.push(id);
    }
  }

  delete(){
    //alert(this.message.length)
    this.message.forEach( (item:any, index:any) => {
      console.log(item);
      this.http.delete('http://localhost:8080/deletingmail/inbox/' + this.myapp.email +"/"+item+"/").subscribe();
    })
    this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
      this.emails = res;
    });
    this.message = new Array();
    this.reload()
  }

  MakeImportant(){
    this.message.forEach( (item:any, index:any) => {
      this.http.get<any[]>('http://localhost:8080/makingimportant/inbox/' + this.myapp.email +"/"+item).pipe(delay(500)).subscribe();
    });
    this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email).pipe(delay(500)).subscribe(res => {
      this.emails = res;
    });
    this.message = new Array();
    this.reload();
  }

  MakeRead(){
    this.message.forEach( (item:any, index:any) => {
      this.http.get<any[]>('http://localhost:8080/makingread/inbox/' + this.myapp.email +"/"+item+"/").pipe(delay(500)).subscribe();
    });
    this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
      this.emails = res;
    });
    this.message = new Array();
    this.reload();
  }

  MakeunRead(){
    this.message.forEach( (item:any, index:any) => {
      this.http.get<any[]>('http://localhost:8080/makingunread/inbox/' + this.myapp.email +"/"+item+"/").pipe(delay(500)).subscribe();
    });
    this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
      this.emails = res;
    });
    this.message = new Array();
    this.reload();
  }

  onOptionsSelected(value:string){
    if(value == 'all'){
      this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
        this.emails = res;
      });
    }else{
      this.http.get<any[]>('http://localhost:8080/' + value + '/inbox/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
        this.emails = res;
      });
    }
  }

  onOptionSortingSelected(value:string){
    if(value == 'none'){
      this.http.get<any[]>('http://localhost:8080/inbox/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
        this.emails = res;
      });
    }else{
      this.http.get<any[]>('http://localhost:8080/' + value + '/inbox/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
      this.emails = res;
      });
    }
  }

  moveToFolders(){
    this.appService.currentMessage.subscribe(message => this.selectedFolders = message);
    if (this.selectedFolders.length > 1){
      alert("select one folder");
      return;
    }
    this.selectedFolders.forEach((folder: any) => {
      this.message.forEach( (item:any) => {
        this.addToFolder(folder, item);
      });
    })
  }

  addToFolder(folder:any, item: any){
    this.http.get<boolean>('http://localhost:8080/addEmailToFolder/' + this.myapp.email +"/"+folder + "/inbox/" + item).subscribe();
  }



}

