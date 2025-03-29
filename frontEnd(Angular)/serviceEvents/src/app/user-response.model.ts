export interface UserResponse {
  email: string; // Emailul utilizatorului
  role: 'USER' | 'ADMIN' | 'ORGANIZER'; // Rolul utilizatorului
}

  