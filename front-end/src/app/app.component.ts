import { state } from '@angular/animations';
import { HttpClient, HttpEvent, HttpEventType } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EventType, Router, Routes } from '@angular/router';
import { fabric } from 'fabric';
import { AppRoutingModule } from './app-routing.module';
import { InboxComponent } from './inbox/inbox.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { saveAs } from 'file-saver';
//import 'fabric-history';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],

})
export class AppComponent implements OnInit{
  [x: string]: any;
  title(title: any) {
    throw new Error('Method not implemented.');
  }

  isChecked = false;
  WriteMessage = false;
  openSearchFrom = false;
  isAddContact = false;                                              /**#####################contact################### */
  menu = true;
  login = true;
  Message = true;
  signup = false;
  email = "";
  id = 0;
  searchKey = '';
  constructor(private formBuilder: FormBuilder,router: Router,private http:HttpClient){}
  x = SidebarComponent
  searchFrom = new FormGroup({
    from: new FormControl(null),
    to: new FormControl(null),
    subject: new FormControl(null),
    body: new FormControl(null),
    date: new FormControl(null),
    attach:new FormControl(null)
  });

  addContactForm = new FormGroup({
    nameContact: new FormControl(null, Validators.required),
    emailContact: new FormControl(null, Validators.required)
  });

  checkMessageForm = new FormGroup({
    to: new FormControl(null, Validators.required),
    subject: new FormControl(null, Validators.required),
    content: new FormControl(null, [Validators.required])
  });

  ngOnInit(): void {
    throw new Error('Method not implemented.');

  }
  files: File[] = [];
  onFileSelected(event:any){
    console.log(event)
    let x = <File>event.target.files[0];
    console.log(x);
    this.files =  <File[]>event.target.files;

  }

  onSubmit(){
    console.warn('Your order has been submitted', this.checkMessageForm.value);
  }
  onSubmitContact(){
    console.warn('Your order has been submitted', this.addContactForm.value);
  }

  close(){
    this.http.delete('http://localhost:8080/deletingmail/' + this.email +"/"+this.id+"/").subscribe();
      const body = {
        "sender": this.email,
        "receiver": this.checkMessageForm.controls.to.value,
        "subject" : this.checkMessageForm.controls.subject.value,
        "body" : this.checkMessageForm.controls.content.value,
        "id" : this.id,
        "important" : false,
        "read" : false,
        "importance": (<HTMLInputElement>document.getElementById("importance")).value,
        "date" : Date.now(),
      }
      this.http.post<boolean>('http://localhost:8080/draftingmail/' + this.email +"/", body).subscribe(data => {
          this.WriteMessage = false;
      })
  }

  openMessageWindow(){
    if (this.WriteMessage){
      this.WriteMessage = false;
    }else{
      this.WriteMessage = true;
    }
  }
  openAddContactWindow(){
    if (this.isAddContact){
      this.isAddContact = false;
    }else{
      this.isAddContact = true;
    }
  }
  openSearchOptions(){
    //this.download(1672600870180);
    //alert("ssss")
    if (this.openSearchFrom){
      this.openSearchFrom = false;
    }else{
      this.openSearchFrom = true;
    }
  }

  activeButton:String = "Inbox";
  static flag:boolean = true;

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

    openMenu(){
      alert(this.menu);
      this.menu = !this.menu;
    }

    addContact(){
      console.warn('Your order has been submitted', this.addContactForm.value);
      const contactEmail = this.addContactForm.controls.emailContact.value;
      this.http.post<boolean>('http://localhost:8080/addcontact/' + this.email +"/" +  contactEmail, '').subscribe();
      this.isAddContact = false;
      this.addContactForm = new FormGroup({
        nameContact: new FormControl(null, Validators.required),
        emailContact: new FormControl(null, Validators.required)
      });
    }
    send(){
      this.id = new Date(Date.now()).getTime();
      const body = {
        "sender": this.email,
        "receiver": this.checkMessageForm.controls.to.value,
        "subject" : this.checkMessageForm.controls.subject.value,
        "body" : this.checkMessageForm.controls.content.value,
        "id" : this.id,
        "important" : false,
        "read" : false,
        "importance": (<HTMLInputElement>document.getElementById("importance")).value,
        "date" : " ",
      }
      this.http.post<boolean>('http://localhost:8080/sendingmail/' + this.email +"/", body).subscribe(data => {

            var formData = new FormData();
            let x = (<HTMLInputElement>document.getElementById("files")).files
            if(x!=null)
              for (const file of this.files) { formData.append('files', file, file.name); }
            alert(this.id);
            this.id = 0;
            if(!data){
              alert("error");
            }else{
              this.WriteMessage = false;
              this.checkMessageForm = new FormGroup({
                to: new FormControl(null, Validators.required),
                subject: new FormControl(null, Validators.required),
                content: new FormControl(null, [Validators.required])
              });
              if((<HTMLInputElement>document.getElementById("files")).files?.length!=0){

                this.http.post<boolean>('http://localhost:8080/uploadattachment/' + this.email +
                  "/" + body.receiver+"/"+body.id+"/",formData).subscribe();
                }
            }
      })
    }

    allSearch(){
      this.openSearchFrom = false;
      const body = {
        "sender": this.searchFrom.controls.from.value,
        "receiver": this.searchFrom.controls.to.value,
        "subject" : this.searchFrom.controls.subject.value,
        "body" : this.searchFrom.controls.body.value,
        "id" : 0,
        "important" : false,
        "read" : false,
        "importance": (<HTMLInputElement>document.getElementById("_importance")).value,
        "date" : this.searchFrom.controls.date.value,
        "hasAttachment": this.isChecked
      }
      return body;
      // console.log(body);

      // this.http.post<any[]>('http://localhost:8080/allSearch/' + this.activeButton + "/" + this.email +"/", body).subscribe(data => {

      //       if(!data){
      //         alert("error");
      //       }else{
      //         console.log("hi", data);
      //         //this.WriteMessage = false;
      //         this.searchFrom = new FormGroup({
      //           from: new FormControl(null),
      //           to: new FormControl(null),
      //           subject: new FormControl(null),
      //           body: new FormControl(null),
      //           date: new FormControl(null),
      //           attach:new FormControl(null)
      //         });
      //       }
      // })
    }

    filenames: string[] = [];

    download(id:any){
      this.filenames = [];
      this.http.get('http://localhost:8080/downloadattachment/'+this.email+"/"+id+"/",{
        reportProgress:true,
        observe: 'events',
        responseType:'blob'
      }).subscribe(
        event => {
          console.log(event);
          this.resportProgress(event);
        }
      );
  }
  private resportProgress(httpEvent: HttpEvent<string[] | Blob>): void {
    switch(httpEvent.type) {
      case HttpEventType.UploadProgress:
        this.updateStatus(httpEvent.loaded, httpEvent.total!, 'Uploading... ');
        break;
      case HttpEventType.DownloadProgress:
        this.updateStatus(httpEvent.loaded, httpEvent.total!, 'Downloading... ');
        break;
      case HttpEventType.ResponseHeader:
        console.log('Header returned', httpEvent);
        break;
      case HttpEventType.Response:
        if (httpEvent.body instanceof Array) {
          this.fileStatus.status = 'done';
          for (const filename of httpEvent.body) {
            this.filenames.unshift(filename);
          }
        } else {
          saveAs(new File([httpEvent.body!], httpEvent.headers.get('File-Name')!,
                  {type: `${httpEvent.headers.get('Content-Type')};charset=utf-8`}));
          // saveAs(new Blob([httpEvent.body!],
          //   { type: `${httpEvent.headers.get('Content-Type')};charset=utf-8`}),
          //    httpEvent.headers.get('File-Name'));
        }
        this.fileStatus.status = 'done';
        break;
        default:
          console.log(httpEvent);
          break;

    }
  }
  fileStatus = { status: '', requestType: '', percent: 0 };
  private updateStatus(loaded: number, total: number, requestType: string): void {
    this.fileStatus.status = 'progress';
    this.fileStatus.requestType = requestType;
    this.fileStatus.percent = Math.round(100 * loaded / total);
  }

  closeContact(){
    this.isAddContact = false;
  }




}
