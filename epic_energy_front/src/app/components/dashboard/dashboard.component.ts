import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ResponseBody } from '../../interfaces/all-clients-response';
import { ClientServiceService } from '../../services/client-service.service';
import { AuthServiceService } from '../../services/auth-service.service';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.css',
    standalone: false
})
export class DashboardComponent implements OnInit {
  clients: ResponseBody | null = null;
  mood: string = 'reading';
  isAdmin: boolean = false;
  role: string = 'USER';
  name: any;
  surname: any;
  selectedItem: string = 'dashboard';

  invoice: iInvoices = {
    date: new Date(),
    amount: 0,
    invoiceNumber: '',
    status: 'PENDING',
    clientId: 0,
  };

  constructor(
    private http: HttpClient,
    private clientService: ClientServiceService,
    private authService: AuthServiceService
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
    const token = this.authService.getToken();
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

  createInvoices() {
    const token = localStorage.getItem('token');
    this.getRoleFromToken();

    console.log('Invoice Number:', this.invoice.invoiceNumber);

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    this.http
      .post<iInvoices>(this.apiUrl + '/invoices', this.invoice, { headers })
      .subscribe(
        (response) => {
          alert('Fattura creata con successo!');
          this.mood = 'reading';
          console.log('Fattura creata:', response);
        },
        (error) => {
          console.error(error);
          alert('Errore durante la creazione della fattura.');
        }
      );
  }
}
