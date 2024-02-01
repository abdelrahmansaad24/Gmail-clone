import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';


import { AppComponent } from './app.component';
import {HttpClientModule} from '@angular/common/http';
import { Route, RouterModule } from '@angular/router';
import { SidebarComponent } from './sidebar/sidebar.component';
import { InboxComponent } from './inbox/inbox.component';
import { SentComponent } from './sent/sent.component';
import { DraftsComponent } from './drafts/drafts.component';
import { TrashComponent } from './trash/trash.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AttachmentComponent } from './attachment/attachment.component';
import { ImportantComponent } from './important/important.component';
import { MessageComponent } from './message/message.component';
import { LogInPageComponent } from './log-in-page/log-in-page.component';
import { SignupComponent } from './signup/signup.component';
import { ReadMessageComponent} from './read-message/read-message.component';
import { ContactsComponent } from './contacts/contacts.component';
import { TestComponent } from './test/test.component';
import { FolderComponent } from './folder/folder.component';



@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    InboxComponent,
    SentComponent,
    DraftsComponent,
    TrashComponent,
    AttachmentComponent,
    ImportantComponent,
    MessageComponent,
    LogInPageComponent,
    SignupComponent,
    ReadMessageComponent,
    ContactsComponent,
    TestComponent,
    FolderComponent,

  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot([
      { path: 'Inbox', component: InboxComponent },
      { path: 'Sent', component: SentComponent },
      { path: 'Draft', component: DraftsComponent },
      { path: 'Trash', component: TrashComponent},
      { path: 'Contact', component: ContactsComponent},
      { path: 'Important', component: ImportantComponent},
      { path: 'readMessage/:name/:from/:to/:subject/:content/:time/:id/:hasAttachment', component: ReadMessageComponent},
      { path: 'folder/:name', component: FolderComponent},
    ]),
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [AppComponent, ReadMessageComponent, InboxComponent, SidebarComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
