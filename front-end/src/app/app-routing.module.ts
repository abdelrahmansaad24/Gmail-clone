import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DraftsComponent } from './drafts/drafts.component';
import { InboxComponent } from './inbox/inbox.component';
import { SentComponent } from './sent/sent.component';
import { TrashComponent } from './trash/trash.component';

const routes: Routes = [];

@NgModule({ 
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
