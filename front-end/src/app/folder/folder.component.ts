import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-folder',
  templateUrl: './folder.component.html',
  styleUrls: ['./folder.component.css']
})
export class FolderComponent implements OnInit{
  constructor(private route:ActivatedRoute, private http:HttpClient,private myapp:AppComponent) {}
  folderName: any;
  emails:any = [];

  ngOnInit(): void {
    this.route.params.subscribe(routeParams => {
      this.folderName = routeParams['name'];
      this.http.get<any[]>('http://localhost:8080/getFolderMails/' + this.myapp.email +"/" + this.folderName).subscribe(data =>{
          this.emails = data;
      });
    });
  }
}
