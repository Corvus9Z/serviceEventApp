import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, AbstractControl } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../AuthService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule]
})
export class CreateEventComponent implements OnInit {
  createEventForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!(this.authService.isAdmin() || this.authService.isOrganizer())) {
      this.router.navigate(['/']);
    }

    this.createEventForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
      description: ['', [Validators.required, Validators.minLength(20)]],
      startDate: ['', [Validators.required, this.futureOrPresentValidator]],
      endDate: ['', Validators.required],
      location: ['', Validators.required]
    });

    // ✅ Validare ca endDate >= startDate
    this.createEventForm.get('endDate')?.valueChanges.subscribe(() => {
      this.validateEndDate();
    });
    this.createEventForm.get('startDate')?.valueChanges.subscribe(() => {
      this.validateEndDate();
    });
  }

  get f() {
    return this.createEventForm.controls;
  }

  futureOrPresentValidator(control: AbstractControl): { [key: string]: any } | null {
    const selectedDate = new Date(control.value);
    const now = new Date();
    if (selectedDate < now) {
      return { notFutureOrPresent: true };
    }
    return null;
  }

  // ✅ Funcție pentru validare endDate
  validateEndDate(): void {
    const start = this.createEventForm.get('startDate')?.value;
    const end = this.createEventForm.get('endDate')?.value;

    if (start && end && new Date(end) < new Date(start)) {
      this.createEventForm.get('endDate')?.setErrors({ endBeforeStart: true });
    } else {
      const errors = this.createEventForm.get('endDate')?.errors;
      if (errors) {
        delete errors['endBeforeStart'];
        if (Object.keys(errors).length === 0) {
          this.createEventForm.get('endDate')?.setErrors(null);
        } else {
          this.createEventForm.get('endDate')?.setErrors(errors);
        }
      }
    }
  }

  onSubmit(): void {
    if (this.createEventForm.invalid) {
      return;
    }

    const { title, description, startDate, endDate, location } = this.createEventForm.value;

    this.authService.createEvent(
      title,
      description,
      new Date(startDate).toISOString(),
      new Date(endDate).toISOString(),
      location
    ).subscribe(
      response => {
        this.router.navigate(['/']);
      },
      error => {
        console.error('Error creating event:', error);
      }
    );
  }
}
