import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AppComponent } from '../app.component';
import { HttpClient } from '@angular/common/http';
import { AppService } from '../app.service';
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
})


export class SidebarComponent implements OnInit {
  @Output() messageEvent = new EventEmitter<string>();
  activeButton:String = "Inbox";
  menu: boolean = this.myapp.menu;
  static flag:boolean = true;
  foldersNames : string[] = [];
  selectedFolders: string[] = [];

  constructor(private myapp:AppComponent, private http:HttpClient, private appService : AppService) { }

  ngOnInit(): void {
    // this.foldersNames.push("hello");
    // this.foldersNames.push("ahsraf");
    // this.foldersNames.push("ahs");
    this.http.get<any[]>('http://localhost:8080/getFoldersNames/'+ this.myapp.email).subscribe(res => {
      console.log(res);
        this.foldersNames = res;
    });
  }
  openMessageWindow(){
    this.myapp.openMessageWindow();
    if(this.myapp.id==0){
      this.myapp.id = new Date(Date.now()).getTime();
    }
  }

    onButtonGroupClick($event: { target: any; srcElement: any; }){
      let clickedElement = $event.target || $event.srcElement;
      // alert(clickedElement.nodeName)
      // alert(clickedElement.className)
      clickedElement.className += " active";
      if( clickedElement.nodeName === "div" ) {


        let isCertainButtonAlreadyActive = clickedElement.parentElement.querySelector(".active");
        // if a Button already has Class: .active
        if( isCertainButtonAlreadyActive ) {
          isCertainButtonAlreadyActive.classList.remove("active");
        }

        clickedElement.className += " active";
      }

    }
    setActive(buttonName: String){
      this.activeButton = buttonName;
    }

    isActive(buttonName: String){
      return this.activeButton === buttonName;
    }

    static openSidebar(){
      //menu = !menu;
    }

    addFolder(){
      const nameFolder = (<HTMLInputElement>document.getElementById("folderName")).value
      if(nameFolder == null || nameFolder == ''){
        alert("name of folder does not exist")
        return;
      }
      if(this.foldersNames.includes(nameFolder)){
        alert("name of folder already exist");
        return;
      }
      this.http.get<boolean>('http://localhost:8080/createFolder/'+ this.myapp.email + "/" + nameFolder + "/").subscribe(res => {
        console.log(res);
        this.reload();
      });

    }

    reload(){
      this.http.get<any[]>('http://localhost:8080/getFoldersNames/'+ this.myapp.email).subscribe(res => {
        this.foldersNames = res;
      });
    }
    print(){
      console.log(this.selectedFolders);
    }
  check(name:any){
    //alert(id)
    let x = false;
    this.selectedFolders.forEach( (item:any, index:any) => {
      if(item === name) {
        x = true;
        this.selectedFolders.splice(index,1);
      }
    })
    if(!x){
      this.selectedFolders.push(name);
    }
    console.log(this.selectedFolders);
    this.appService.changeMessage(this.selectedFolders);
  }

  deleteFolder(){
    if(this.selectedFolders.length != 1){
      alert("select one folder");
      return;
    }
    this.http.get<boolean>('http://localhost:8080/deleteFolder/'+ this.myapp.email + "/" + this.selectedFolders).subscribe(res => {
      console.log(res);
      this.reload();
      this.selectedFolders = [];
    });
  }

  renemeFolder(){
    const nameFolder = (<HTMLInputElement>document.getElementById("folderName")).value;
    if(nameFolder == null || nameFolder == ''){
      alert("name of folder does not exist")
      return;
    }
    if(this.selectedFolders.length != 1){
      alert("select one folder");
      return;
    }
    this.http.get<boolean>('http://localhost:8080/renameFolder/'+ this.myapp.email + "/" + this.selectedFolders  + "/" + nameFolder).subscribe(res => {
        console.log("rename ", res);
        this.reload();
        this.selectedFolders = [];
    });
  }
}





