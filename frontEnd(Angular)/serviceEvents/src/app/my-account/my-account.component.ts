import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../services/api.service';
import { AuthService } from '../AuthService';
import { Router } from '@angular/router';


@Component({
  selector: 'app-my-account',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {
  user: any = {};
  toastMessage: string = '';
  showToast: boolean = false;

  constructor(
    private apiService: ApiService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const currentUser = this.authService.getCurrentUser();

    if (currentUser) {
      this.user = {
        id: currentUser.id,
        name: currentUser.name,
        lastName: currentUser.lastName,
        email: currentUser.email,
        password: '***unchanged***', // ✅ placeholder pentru parolă nemodificată
        roles: [{ name: currentUser.role }] // ✅ convertim string → listă de obiecte
      };
    }
  }

  saveChanges(): void {
    if (!this.user || !this.user.id) return;
  
    const dataToSend = { ...this.user };
  
    if (this.user.password === '***unchanged***') {
      dataToSend.password = '***unchanged***';
    }
  
    console.log('📤 Trimitem către backend:', dataToSend);
  
    this.apiService.updateUser(this.user.id, dataToSend).subscribe({
      next: (response) => {
        console.log('✅ User updated:', response);
        sessionStorage.setItem('showAccountUpdateToast', 'true'); // 🟢 Setăm flag
        this.router.navigateByUrl('/');
      },
      error: (err) => {
        console.error('❌ Failed to update user:', err);
      }
    });
  }
  


}
