import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';
import { AuthService } from '../AuthService';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-my-created-events',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './my-created-events.component.html',
  styleUrls: ['./my-created-events.component.css']
})
export class MyCreatedEventsComponent implements OnInit {
  events: any[] = [];
  loading: boolean = true;
  errorMessage: string = '';
  successMessage: string = '';
  editingEvent: any | null = null;
  showDeleteModal: boolean = false;
  eventToDelete: any | null = null;
  animationClass: string = '';

  // ✅ Toast-uri
  showEditToast: boolean = false;
  showDeleteToast: boolean = false;

  constructor(
    public apiService: ApiService,
    public authService: AuthService,
    public router: Router
  ) {}

  ngOnInit(): void {
    this.loadMyCreatedEvents();
  }

  loadMyCreatedEvents(): void {
    this.clearMessages();
    const userId = this.authService.getCurrentUserId();

    if (!userId) {
      this.errorMessage = "User ID not available.";
      return;
    }

    this.apiService.getMyCreatedEventsOrganizer(userId).subscribe(
      (response: any) => {
        if (response?.data) {
          this.events = response.data;
        }
        this.loading = false;
      },
      (error: any) => {
        console.error("❌ Error fetching created events:", error);
        this.errorMessage = "Could not load your events.";
        this.loading = false;
      }
    );
  }

  editEvent(event: any): void {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser) {
      this.errorMessage = "You need to be logged in to edit an event.";
      return;
    }

    if (currentUser.role === 'ADMIN' || (currentUser.role === 'ORGANIZER' && event.organizer?.id === currentUser.id)) {
      this.editingEvent = { ...event };
    } else {
      this.errorMessage = "You are not allowed to edit this event.";
    }
  }

  saveEvent(): void {
    if (!this.editingEvent) return;
  
    this.clearMessages();
  
    this.apiService.updateEvent(this.editingEvent.id, this.editingEvent).subscribe(
      () => {
        this.editingEvent = null;
        this.loadMyCreatedEvents();
  
        // Așteaptă puțin până se încarcă UI-ul, apoi afișează toast-ul
        setTimeout(() => {
          this.showEditToast = true;
          setTimeout(() => this.showEditToast = false, 3000);
        }, 300);
      },
      (error: any) => {
        console.error("❌ Error updating event:", error);
        this.errorMessage = "Could not update event.";
      }
    );
  }
  
  

  openDeleteModal(event: any): void {
    this.eventToDelete = event;
    this.animationClass = 'fade-in';
    this.showDeleteModal = true;
  }

  confirmDelete(): void {
    if (!this.eventToDelete) return;
  
    const eventId = this.eventToDelete.id;
  
    this.apiService.deleteEvent(eventId).subscribe(
      () => {
        this.closeDeleteModal();
        this.loadMyCreatedEvents();
  
        // Așteaptă puțin până se încarcă lista, apoi afișează toast-ul
        setTimeout(() => {
          this.showDeleteToast = true;
          setTimeout(() => this.showDeleteToast = false, 3000);
        }, 300);
      },
      (error: any) => {
        console.error("❌ Error deleting event:", error);
        this.errorMessage = "Could not delete event.";
        this.closeDeleteModal();
      }
    );
  }
  
  

  closeDeleteModal(): void {
    this.animationClass = 'fade-out';
    setTimeout(() => {
      this.showDeleteModal = false;
      this.eventToDelete = null;
    }, 300);
  }

  clearMessages(): void {
    this.errorMessage = '';
    this.successMessage = '';
  }
}
