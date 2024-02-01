import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { FormGroup, FormControl } from '@angular/forms';
import { AppService } from '../app.service';
import { delay } from 'rxjs/internal/operators/delay';

@Component({
  selector: 'app-important',
  templateUrl: './important.component.html',
  styleUrls: ['./important.component.css']
})
export class ImportantComponent implements OnInit {

  emails:any = [];
  selectedFolders: string[] = [];
  constructor(private http:HttpClient,private myapp:AppComponent, private  appService: AppService) { }
  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/catchImportant/'+this.myapp.email).pipe(delay(500)).subscribe(res => {
      this.emails = res;
    });
  }

  reload(){
    this.http.get<any[]>('http://localhost:8080/catchImportant/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
      this.emails = res;
    });
  }

  open(id:any){
    this.http.get<any[]>('http://localhost:8080/makingread/important/'+this.myapp.email+"/" + id +"/").pipe(delay(500)).subscribe();
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
      this.http.delete('http://localhost:8080/deletingmail/' + this.myapp.email +"/"+item+"/").pipe(delay(500)).subscribe();
    })
    this.message= new Array();
    this.reload();
  }

  makeUnimportant(){
    this.message.forEach( (item:any, index:any) => {
      this.http.get<any[]>('http://localhost:8080/makingunimportant/' + this.myapp.email +"/"+item).pipe(delay(500)).subscribe();
    });
    this.message = new Array();
    this.reload();
  }

  MakeRead(){
    this.message.forEach( (item:any, index:any) => {
      this.http.get<any[]>('http://localhost:8080/makingread/important/' + this.myapp.email +"/"+item+"/").pipe(delay(500)).subscribe();
    });
    this.http.get<any[]>('http://localhost:8080/catchImportant/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
      this.emails = res;
    });
    this.message = new Array();
    this.reload();
  }

  MakeunRead(){
    this.message.forEach( (item:any, index:any) => {
      this.http.get<any[]>('http://localhost:8080/makingunread/important/' + this.myapp.email +"/"+item+"/").pipe(delay(500)).subscribe();
    });
    this.http.get<any[]>('http://localhost:8080/catchImportant/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
      this.emails = res;
    });
    this.message = new Array();
    this.reload();
  }
  Search(){
    if (this.myapp.openSearchFrom){
      this.myapp.openSearchFrom = false;
      const body = this.myapp.allSearch();
      this.http.post<any[]>('http://localhost:8080/allSearch/important/' +  this.myapp.email +"/", body).pipe(delay(500)).subscribe(data => {

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
      this.http.get<any[]>('http://localhost:8080/search/important/'+ this.myapp.email + "/" + searchKey +"/").pipe(delay(500)).subscribe(res => {
        this.emails = res;
      });
    }
  }

  onOptionsSelected(value:string){
    if(value == 'all'){
      this.http.get<any[]>('http://localhost:8080/catchImportant/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
        this.emails = res;
      });
    }else{
      this.http.get<any[]>('http://localhost:8080/' + value + '/important/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
        this.emails = res;
      });
    }

  }

  onOptionSortingSelected(value:string){
    if(value == 'none'){
      this.http.get<any[]>('http://localhost:8080/catchImportant/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
        this.emails = res;
      });
    }else{
      this.http.get<any[]>('http://localhost:8080/' + value + '/important/'+this.myapp.email+"/").pipe(delay(500)).subscribe(res => {
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
    this.reload();
  }

  addToFolder(folder:any, item: any){
    this.http.get<boolean>('http://localhost:8080/addEmailToFolder/' + this.myapp.email +"/"+folder + "/inbox/" + item).pipe(delay(500)).subscribe();
  }


}
