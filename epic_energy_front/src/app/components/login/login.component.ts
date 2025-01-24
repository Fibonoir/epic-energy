import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment.development';
import { UserRegistration } from '../../interfaces/user-interfaces';

interface Response {
  token: string;
}

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
    standalone: false
})
export class LoginComponent {

  apiUrl = environment.apiUrl;

  username: string = '';
  email: string = '';
  password: string = '';
  selectedItem: string = 'Login';
  isAdmin: boolean = false;
  role: string = 'user';
  adminKey: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  login() {

    this.http
      .post<Response>(this.apiUrl + '/auth/login', {
        username: this.email,
        password: this.password,
      })
      .subscribe((response: Response) => {
        if (!response.token) {
          console.log('Login failed');
          return;
        }
        localStorage.setItem('token', response.token);
        this.router.navigate(['/dashboard']);
      });
  }

  register() {
    const payload: UserRegistration = {
      username: this.username,
      password: this.password,
      email: this.email,
      adminKey: this.adminKey,
      role: 'user'
    };

    if (this.isAdmin) {
      payload.role = 'admin';
    }

    this.http.post<string>(this.apiUrl + '/auth/register', payload).subscribe({
      next: (response: string) => {
        console.log('Registration successful:', response);

        // Reset fields
        this.username = '';
        this.email = '';
        this.password = '';
        this.adminKey = '';

        // Navigate to the home page
        this.router.navigate(['/']);
      },
      error: (error) => {
        console.error('Registration failed:', error);

              },
      complete: () => {
        console.log('Registration request completed.');
      }
    });
  }

}
