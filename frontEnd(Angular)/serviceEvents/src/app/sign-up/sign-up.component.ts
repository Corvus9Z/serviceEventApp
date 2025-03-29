import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; // Importă clasele necesare pentru formulare reactive
import { AuthService } from '../AuthService'; // Asigură-te că calea este corectă
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; // Importă CommonModule

@Component({
  selector: 'app-sign-up', // Selectorul pentru a include acest component
  standalone: true, // Indică faptul că este un component standalone
  templateUrl: './sign-up.component.html', // URL-ul pentru fișierul HTML asociat cu acest component
  styleUrls: ['./sign-up.component.css'], // URL-ul pentru fișierul CSS asociat cu acest component
  imports: [ReactiveFormsModule, CommonModule] // Adaugă CommonModule aici
})
export class SignUpComponent {
  signUpForm: FormGroup;
  showSuccessToast = false;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {
    this.signUpForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]],
      name: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      role: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.signUpForm.valid) {
      const { email, password, name, lastName, role } = this.signUpForm.value;
  
      this.authService.signUp(email, password, name, lastName, role).subscribe(
        response => {
          console.log('User registered successfully', response);
  
          // ✅ Redirecționează cu query param
          this.router.navigate(['/'], { queryParams: { registered: 'true' } });
        },
        error => {
          console.error('Registration error', error);
        }
      );
    }
  }
  
}




