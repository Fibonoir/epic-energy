import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ResponseBody } from '../../interfaces/all-clients-response';
import { ClientServiceService } from '../../services/client-service.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit {
  clients: ResponseBody | null = null;
  mood: string = 'reading';
  isAdmin: boolean = false;
  role: string = 'USER';
name: any;
surname: any;

  constructor(
    private http: HttpClient,
    private clientService: ClientServiceService
  ) {}

  ngOnInit(): void {
    this.getRoleFromToken();
  }

  fetchAllClients() {
    this.clientService.getAllClients().subscribe({
      next: (response: ResponseBody) => {
        this.clients = response;
        console.log(this.clients);
      },
      error: (error) => {
        console.log('Error' + error);
      },
    });
  }

  getRoleFromToken(): string | null {
    const token = localStorage.getItem('token');
    if (!token) {
      return null;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));

      const roles = payload.roles.map((role: string) => role.toLowerCase());
      if (roles.some((role: string) => role.includes('admin'))) {
        this.isAdmin = true;
        this.role = 'ADMIN';
      }
      console.log(roles);
      return payload.role || null;
    } catch (error) {
      console.error('Error parsing token', error);
      return null;
    }
  }
}
