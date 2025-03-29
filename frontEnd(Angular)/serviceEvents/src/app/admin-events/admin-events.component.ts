import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';
import { AuthService } from '../AuthService';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-events',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-events.component.html',
  styleUrls: ['./admin-events.component.css']
})
export class AdminEventsComponent implements OnInit {
  events: any[] = [];
  loading: boolean = true;
  errorMessage: string = '';
  successMessage: string = '';
  editingEvent: any | null = null;
  showDeleteModal: boolean = false;
  eventToDelete: any | null = null;
  showEditToast: boolean = false;
  showDeleteToast: boolean = false;

  constructor(
    public apiService: ApiService,
    public authService: AuthService,
    public router: Router
  ) {}

  ngOnInit(): void {
    this.loadAllEvents();
  }

  loadAllEvents(): void {
    this.apiService.getAllEvents().subscribe(
      (response: any) => {
        if (response?.data) {
          this.events = response.data;
        }
        this.loading = false;
      },
      (error: any) => {
        console.error("❌ Error fetching all events:", error);
        this.errorMessage = "Could not load events.";
        this.loading = false;
      }
    );
  }

  editEvent(event: any): void {
    this.editingEvent = { ...event };
  }

  saveEvent(): void {
    if (!this.editingEvent) return;

    this.apiService.updateEvent(this.editingEvent.id, this.editingEvent).subscribe(
      () => {
        this.editingEvent = null;
        this.loadAllEvents();
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
    this.showDeleteModal = true;
  }

  confirmDelete(): void {
    if (!this.eventToDelete) return;

    this.apiService.deleteEvent(this.eventToDelete.id).subscribe(
      () => {
        this.loadAllEvents();
        this.closeDeleteModal();
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
    this.showDeleteModal = false;
    this.eventToDelete = null;
  }
}
