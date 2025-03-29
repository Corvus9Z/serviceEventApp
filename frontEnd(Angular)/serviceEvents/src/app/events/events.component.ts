import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';
import { CommonModule } from '@angular/common';
import { AuthService } from '../AuthService';

@Component({
  standalone: true,
  imports: [CommonModule],
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
  events: any[] = []; 
  joinedEvents: number[] = [];
  loading: boolean = true;
  errorMessage: string = '';
  successMessage: string = ''; // ✅ Toast pentru succes

  constructor(private apiService: ApiService, private authService: AuthService) {}

  ngOnInit(): void {
    this.loadEvents();
    this.loadUserJoinedEvents();
  }

  loadEvents(): void {
    this.apiService.getEvents().subscribe(
      data => {
        console.log('Events received:', data);
        this.events = data.data;
        console.log('🔍 Structure of each event object:', this.events);
        this.loading = false;
      },
      error => {
        console.error("Error fetching events:", error);
        this.loading = false;
      }
    );
  }

  loadUserJoinedEvents(): void {
    const userId = this.authService.getCurrentUserId();
    if (userId) {
      console.log(`🔄 Fetching joined events for user ${userId}...`);
      this.apiService.getUserEvents(userId).subscribe(
        (response: any) => {
          console.log("📢 API response for joined events:", response);
          if (response && response.data && Array.isArray(response.data)) {
            this.joinedEvents = response.data.map((event: any) => event.id);
            console.log("✅ Updated joinedEvents:", this.joinedEvents);
          } else {
            console.error("❌ API response does not contain valid `data` array:", response);
          }
        },
        (error: any) => console.error("❌ Error fetching joined events:", error)
      );
    }
  }

  isUserJoined(eventId: number): boolean {
    console.log(`🔍 Checking if user is joined to event ${eventId}. Current joinedEvents:`, this.joinedEvents);
    const joined = this.joinedEvents.includes(eventId);
    console.log(`🔍 isUserJoined(${eventId}):`, joined);
    return joined;
  }

  showPopup(message: string): void {
    console.log("Popup message:", message);
    this.errorMessage = message;
    setTimeout(() => this.closePopup(), 3000);
  }

  showSuccessToast(message: string): void {
    this.successMessage = message;
    setTimeout(() => this.successMessage = '', 3000);
  }

  closePopup(): void {
    this.errorMessage = '';
  }

  isUser(): boolean {
    return this.authService.isUser();
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  isOrganizer(): boolean {
    return this.authService.isOrganizer();
  }

  joinEvent(eventId: number): void {
    console.log("🔍 Trying to join event:", eventId);

    const userRole = this.authService.getCurrentUser()?.role;

    if (!userRole) {
      console.log("❌ No user role found. User is not logged in.");
      this.showPopup("You need an account to join events.");
      return;
    }

    if (userRole === "ADMIN") {
      console.log("❌ Admins cannot join events.");
      this.showPopup("Admins cannot join events.");
      return;
    }

    if (userRole === "ORGANIZER") {
      console.log("❌ Organizers do not need to join their own events.");
      this.showPopup("As an organizer, you do not need to join events.");
      return;
    }

    if (userRole !== "USER") {
      console.log("❌ Invalid role for joining events.");
      this.showPopup("Only users can join events.");
      return;
    }

    console.log("✅ User is logged in as USER.");
    const userId = this.authService.getCurrentUserId();
    console.log("🔍 Retrieved user ID:", userId);

    if (!userId) {
      console.log("❌ No user ID found.");
      this.showPopup("User ID not available.");
      return;
    }

    if (this.isUserJoined(eventId)) {
      console.log("❌ User is already joined. NO REQUEST SENT TO BACKEND.");
      this.showPopup("You are already joined to this event.");
      return;
    }

    console.log("📩 Sending join request to API...");
    this.apiService.joinEvent(eventId, userId).subscribe(
      response => {
        console.log("✅ Successfully joined event:", response);
        this.joinedEvents.push(eventId);
        this.loadUserJoinedEvents();
        this.showSuccessToast("You've successfully joined this event! ✅");
      },
      error => {
        console.error("❌ API returned an error:", error);
        this.showPopup("Could not join the event. Please try again.");
      }
    );
  }
}
