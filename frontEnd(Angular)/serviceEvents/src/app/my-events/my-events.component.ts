import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importă CommonModule pentru *ngIf și *ngFor
import { Router } from '@angular/router';
import { ApiService } from '../services/api.service';
import { AuthService } from '../AuthService';
@Component({
  selector: 'app-my-events',
  templateUrl: './my-events.component.html',
  styleUrls: ['./my-events.component.css'],
  standalone: true, // Specifică că această componentă este standalone
  imports: [CommonModule] // Adaugă CommonModule pentru a putea folosi *ngIf și *ngFor
})
export class MyEventsComponent implements OnInit {
  myEvents: any[] = []; // Variabilă pentru a stoca lista de evenimente ale utilizatorului

  constructor(private apiService: ApiService, private router: Router,private authService:AuthService) {} // Injectează ApiService și Router

  ngOnInit(): void {
    this.loadMyEvents(); // Apelează metoda pentru a încărca evenimentele la care utilizatorul este înscris
  }
  loadMyEvents(): void {
    const userId = this.authService.getCurrentUserId();
    if (!userId) {
      console.error("User ID is missing. Cannot fetch events.");
      return;
    }
  
    this.apiService.getUserEvents(userId).subscribe(
      (response: any) => {
        console.log("Raw response from backend:", response);
  
        // Verificăm structura răspunsului
        if (response.status === 'succes' && Array.isArray(response.data)) {
          this.myEvents = response.data; // Extrage lista de evenimente
          console.log("Extracted events:", this.myEvents);
        } else {
          console.error("Failed to retrieve events or invalid response structure:", response.message);
          this.myEvents = [];
        }
      },
      error => {
        console.error("Error fetching user events:", error);
        this.myEvents = [];
      }
    );
  }
  
  
  
  getLoggedInUserId(): number | null {
    // Obține ID-ul utilizatorului din localStorage sau alt mecanism de stocare
    const user = localStorage.getItem('loggedInUser');
    return user ? JSON.parse(user).id : null;
  }
  

  viewEventDetails(eventId: number): void {
    this.router.navigate(['/event', eventId]); // Navighează către detaliile unui eveniment specific
  }
}
