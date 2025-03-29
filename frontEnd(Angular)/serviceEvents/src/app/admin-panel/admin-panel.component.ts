import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyCreatedEventsComponent } from '../my-created-events/my-created-events.component';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../services/api.service';
import { AuthService } from '../AuthService';

@Component({
  selector: 'app-admin-panel',
  standalone: true,
  imports: [CommonModule, MyCreatedEventsComponent, FormsModule],
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {
  activeTab: 'myCreated' | 'allEvents' | 'users' | null = null;
  allEvents: any[] = [];
  users: any[] = [];
  currentUserId: number | null = null;

  constructor(
    private apiService: ApiService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const currentUser = this.authService.getCurrentUser();
    this.currentUserId = currentUser?.id || null;
  }

  selectTab(tab: 'myCreated' | 'allEvents' | 'users'): void {
    this.activeTab = tab;
    if (tab === 'allEvents') this.loadAllEvents();
    else if (tab === 'users') this.loadAllUsers();
  }

  loadAllEvents(): void {
    this.apiService.getEvents().subscribe(
      response => this.allEvents = response.data,
      error => console.error("❌ Error loading events", error)
    );
  }

  loadAllUsers(): void {
    this.apiService.getAllUsers().subscribe(
      response => {
        this.users = response.data.filter((user: any) => user.id !== this.currentUserId);
      },
      error => console.error("❌ Error loading users", error)
    );
  }

  getUserRoles(user: any): string {
    if (!user?.roles || user.roles.length === 0) {
      return 'Unspecified';
    }
    return user.roles.map((r: any) => r.name).join(', ');
  }

  editEvent(event: any): void {
    console.log("✏️ Edit event", event);
  }

  deleteEvent(eventId: number): void {
    if (confirm("Are you sure you want to delete this event?")) {
      this.apiService.deleteEvent(eventId).subscribe(() => this.loadAllEvents());
    }
  }

  editUser(user: any): void {
    console.log("✏️ Edit user", user);
  }

  deleteUser(userId: number): void {
    if (confirm("Are you sure you want to delete this user?")) {
      this.apiService.deleteUser(userId).subscribe(() => this.loadAllUsers());
    }
  }
}
