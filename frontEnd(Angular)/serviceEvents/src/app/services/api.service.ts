import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../AuthService';
@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private apiUrl = 'http://localhost:8080/api/events'; // URL-ul corect al backend-ului
  private commentsApiUrl = 'http://localhost:8080/api/comments';

 

  constructor(private http: HttpClient, private authService: AuthService) {}

  // Metodă de testare pentru a obține date de la backend
  getEvents(): Observable<any> {
    return this.http.get(`${this.apiUrl}/allEvents`);
  }
  getEventById(eventId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${eventId}`); 
  }
  getUserEvents(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/my-events/${userId}`);
  }
  
  joinEvent(eventId: number, userId: number): Observable<any> {
    
    
    const eventSignUpData = { eventId, userId };
    return this.http.post('http://localhost:8080/api/signUp', eventSignUpData);
  }
  getMyCreatedEvents(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/my-events/${userId}`);
  }
  getMyCreatedEventsOrganizer(userId: number): Observable<Event[]> {
    return this.http.get<Event[]>(`${this.apiUrl}/my-created-events/${userId}`);
}

  
  
updateEvent(eventId: number, eventData: any): Observable<any> {
  return this.http.put<any>(`${this.apiUrl}/${eventId}`, eventData);
}


  
deleteEvent(eventId: number): Observable<any> {
  return this.http.delete<any>(`${this.apiUrl}/${eventId}`); 
}

getCommentsByEventId(eventId: number) {
  return this.http.get<any>(`${this.commentsApiUrl}/event/${eventId}`);
}


addComment(eventId: number, content: string) {
  const userId = this.authService.getCurrentUserId();
  const body = { eventId, userId, content };
  console.log('Trimitem comentariu:', body);
  return this.http.post<any>(`${this.commentsApiUrl}/createComment`, body);
}



updateUser(userId: number, userData: any) {
  return this.http.put<any>(`http://localhost:8080/api/my-account/update/${userId}`, userData);
}

getAllEvents(): Observable<any> {
  return this.http.get(`${this.apiUrl}/events/allEvents`);
}
getAllUsers(): Observable<any> {
  return this.http.get<any>('http://localhost:8080/api/my-account/all'); // adaptat la ruta ta de backend
}
deleteUser(userId: number): Observable<any> {
  return this.http.delete<any>(`http://localhost:8080/api/my-account/${userId}`);
}

  
}

