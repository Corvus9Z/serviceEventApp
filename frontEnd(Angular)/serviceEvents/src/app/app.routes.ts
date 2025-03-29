import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { EventsComponent } from './events/events.component';
import { EventDetailsComponent } from './event-details/event-details.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { LoginComponent } from './login/login.component';
import { MyEventsComponent } from './my-events/my-events.component';
import { CreateEventComponent } from './create-event/create-event.component';
import { MyCreatedEventsComponent } from './my-created-events/my-created-events.component';
import { MyAccountComponent } from './my-account/my-account.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component'; // ✅ Import nou

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'events', component: EventsComponent },
  { path: 'event/:id', component: EventDetailsComponent },
  { path: 'sign-up', component: SignUpComponent },
  { path: 'login', component: LoginComponent },
  { path: 'create-event', component: CreateEventComponent },
  { path: 'my-events', component: MyEventsComponent },
  { path: 'my-created-events', component: MyCreatedEventsComponent },
  { path: 'my-account', component: MyAccountComponent },
  {
    path: 'admin-panel',
    loadComponent: () =>
      import('./admin-panel/admin-panel.component').then(m => m.AdminPanelComponent)
  },
   // ✅ Adăugat aici
  { path: '**', redirectTo: '' } // Fallback
];
