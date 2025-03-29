import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../AuthService';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  isLoggedIn$!: Observable<boolean>;
  isUser$!: Observable<boolean>;
  isAdmin$!: Observable<boolean>;
  isOrganizer$!: Observable<boolean>;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.isLoggedIn$ = this.authService.currentUser$.pipe(map(user => user !== null));
    this.isUser$ = this.authService.currentUser$.pipe(map(user => user?.role === 'USER'));
    this.isAdmin$ = this.authService.currentUser$.pipe(map(user => user?.role === 'ADMIN'));
    this.isOrganizer$ = this.authService.currentUser$.pipe(map(user => user?.role === 'ORGANIZER'));

    this.isUser$.subscribe(value => console.log('NavbarComponent - isUser:', value));
    this.isAdmin$.subscribe(value => console.log('NavbarComponent - isAdmin:', value));
    this.isOrganizer$.subscribe(value => console.log('NavbarComponent - isOrganizer:', value));
  }

  logout(): void {
    this.authService.logout();
    sessionStorage.setItem('showLogoutToast', 'true');

    if (this.router.url === '/') {
      window.location.reload();
    } else {
      this.router.navigateByUrl('/');
    }
  }
}
