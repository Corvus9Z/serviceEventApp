import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs'; 
import { HttpClient } from '@angular/common/http';
import { tap, catchError } from 'rxjs/operators'; 
import { UserResponse } from './user-response.model';

interface User {
  id: number;
  email: string;
  name: string;
  lastName: string;
  role: 'USER' | 'ADMIN' | 'ORGANIZER';
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { email, password }).pipe(
      tap(response => {
        const userRole = response.data.roles && response.data.roles.length > 0 ? response.data.roles[0].name : undefined;
        this.currentUserSubject.next({
          id: response.data.id,
          email: response.data.email,
          role: userRole,
          name: response.data.name,
          lastName: response.data.lastName
        });
      })
    );
  }

  getCurrentUserId(): number | null {
    const user = this.currentUserSubject.getValue();
    console.log("👤 Retrieved user from currentUserSubject:", user);
    return user ? user.id : null;
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.getValue();
  }

  signUp(
    email: string,
    password: string,
    name: string,
    lastName: string,
    role: string
  ): Observable<UserResponse> {
    const roles = [{ name: role }];
    const body = { email, password, name, lastName, roles };
    return this.http.post<UserResponse>(`${this.apiUrl}/signup`, body, {
      headers: { 'Content-Type': 'application/json' },
    });
  }

  logout(): void {
    this.currentUserSubject.next(null);
  }

  isLoggedIn(): boolean {
    return this.currentUserSubject.getValue() !== null;
  }

  isUser(): boolean {
    return this.currentUserSubject.getValue()?.role === 'USER';
  }

  isAdmin(): boolean {
    return this.currentUserSubject.getValue()?.role === 'ADMIN';
  }

  isOrganizer(): boolean {
    return this.currentUserSubject.getValue()?.role === 'ORGANIZER';
  }

  createEvent(
    title: string,
    description: string,
    startDate: string,
    endDate: string,
    location: string
  ): Observable<any> {
    const user = this.getCurrentUser();
    const organizerId = this.getCurrentUserId();
    const organizerFullName = user ? `${user.name} ${user.lastName}` : 'Unknown';

    const eventData = {
      title,
      description,
      startDate,
      endDate,
      location,
      organizerId,
      organizerFullName // ✅ Adăugat pentru backend
    };

    console.log('Sending event data:', eventData);

    return this.http.post<any>(`${this.apiUrl}/events`, eventData, {
      headers: { 'Content-Type': 'application/json' }
    }).pipe(
      catchError(err => {
        console.error('Error occurred while creating event:', err);
        return throwError(() => err);
      })
    );
  }

  // ✅ Preluăm evenimentele unde userul este înscris
  getUserEvents(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/events/my-events/${userId}`);
  }

  // ✅ Funcție pentru a înscrie utilizatorul la un eveniment
  joinEvent(eventId: number, userId: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/signUp`, { eventId, userId });
  }
}
