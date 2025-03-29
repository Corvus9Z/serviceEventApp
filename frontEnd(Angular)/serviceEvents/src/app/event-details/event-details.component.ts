import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from '../services/api.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-event-details',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css']
})
export class EventDetailsComponent implements OnInit {
  eventId: number | null = null;
  event: any;
  comments: any[] = [];
  newComment: string = '';
  loading: boolean = true;

  toastMessage: string = '';
  showToast: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService
  ) {}

  ngOnInit(): void {
    this.eventId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadEventDetails();
    this.loadComments();
  }

  loadEventDetails(): void {
    this.apiService.getEventById(this.eventId!).subscribe(
      response => {
        this.event = response.data;
        console.log(this.event)
        this.loading = false;
      },
      error => {
        console.error("Error fetching event details:", error);
        this.loading = false;
      }
    );
  }
  loadComments(): void {
    this.apiService.getCommentsByEventId(this.eventId!).subscribe(
      response => {
        console.log("📥 Comments response:", response);
  
        // Verificăm structura
        if (Array.isArray(response)) {
          this.comments = response;
        } else if (Array.isArray(response.data)) {
          this.comments = response.data;
        } else {
          console.warn("❓ Format necunoscut la response, setăm empty:", response);
          this.comments = [];
        }
  
        console.log("✅ Comentarii salvate în componentă:", this.comments);
      },
      error => {
        console.error("❌ Error fetching comments:", error);
      }
    );
  }
  
  

  submitComment(): void {
    if (this.newComment.trim() === '' || !this.eventId) {
      console.error('❌ Comment not sent. Missing eventId or content.');
      return;
    }
  
    this.apiService.addComment(this.eventId, this.newComment).subscribe(
      response => {
        console.log('✅ Comentariu adăugat:', response);
  
        // 🔒 Verificăm că lista există
        if (!this.comments) this.comments = [];
  
        // ✅ Adăugăm comentariul primit (deja e în format corect)
        this.comments.unshift(response);
  
        this.newComment = '';
        this.showSuccessToast('Comment added successfully!');
      },
      error => {
        console.error('❌ Failed to add comment:', error);
      }
    );
  }
  
  
  
  
  

  showSuccessToast(message: string): void {
    this.toastMessage = message;
    this.showToast = true;
    setTimeout(() => {
      this.showToast = false;
      this.toastMessage = '';
    }, 3000);
  }
}
