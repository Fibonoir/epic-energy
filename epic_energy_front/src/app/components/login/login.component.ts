import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

interface Response {
  token: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  email: string = '';
  password: string = '';


  constructor(private http: HttpClient, private router: Router) { }

  login() {
    console.log('Email: ' + this.email);
    console.log('Password: ' + this.password);

    this.http.post<Response>('http://localhost:8080/api/auth/login', {
      username: this.email,
      password: this.password
    }).subscribe((response: Response) => {
      console.log('Response: ' + JSON.stringify(response));

      
      if (!response.token) {
        console.log('Login failed');
        return;
      }
      localStorage.setItem('token', response.token);
      this.router.navigate(['/dashboard']);

    });


  }
}
