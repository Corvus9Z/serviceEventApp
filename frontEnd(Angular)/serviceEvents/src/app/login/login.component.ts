import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../AuthService';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loginErrorMessage: string = ''; // ✅ Pentru toast de eroare

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  onLogin(): void {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;

      this.authService.login(email, password).subscribe(
        response => {
          const { name, lastName } = response.data;
          const fullName = `${name} ${lastName}`;

          this.router.navigate(['/'], {
            state: {
              showLoginSuccessToast: true,
              userFullName: fullName
            }
          });
        },
        error => {
          console.error('Login failed', error);
          this.loginErrorMessage = '❌ Invalid email or password';

          // 🕒 Ascunde toast-ul după 3 secunde
          setTimeout(() => {
            this.loginErrorMessage = '';
          }, 3000);
        }
      );
    }
  }
}
