import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  imports: [CommonModule],
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  events: any[] = [];
  loading: boolean = true;

  showLoginToast = false;
  showLogoutToast = false;
  showAccountUpdatedToast = false;

  userFullName: string = '';
  pageLoaded: boolean = false;

  constructor(private apiService: ApiService, private router: Router) {}

  ngOnInit(): void {
    const state = window.history.state;

    if (state?.showLoginSuccessToast) {
      this.userFullName = state.userFullName || '';
      this.showLoginToast = true;
      console.log("✅ Showing login toast for:", this.userFullName);

      setTimeout(() => {
        this.showLoginToast = false;
      }, 3000);
    }

    const showLogout = sessionStorage.getItem('showLogoutToast');
    if (showLogout === 'true') {
      this.showLogoutToast = true;
      console.log("🔴 Showing logout toast");

      setTimeout(() => {
        this.showLogoutToast = false;
        sessionStorage.removeItem('showLogoutToast');
      }, 3000);
    }

    const showAccount = sessionStorage.getItem('showAccountUpdateToast');
    if (showAccount === 'true') {
      this.showAccountUpdatedToast = true;
      console.log("🟢 Showing account updated toast");

      setTimeout(() => {
        this.showAccountUpdatedToast = false;
        sessionStorage.removeItem('showAccountUpdateToast');
      }, 3000);
    }

    this.apiService.getEvents().subscribe(
      response => {
        this.events = response.data;
        this.loading = false;
      },
      error => {
        console.error("Error fetching events:", error);
        this.loading = false;
      }
    );
  }
}
