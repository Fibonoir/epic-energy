import { iInvoices } from './../../interfaces/invoices';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ResponseBody } from '../../interfaces/all-clients-response';
import { ClientServiceService } from '../../services/client-service.service';
import { AuthServiceService } from '../../services/auth-service.service';
import { environment } from '../../../environments/environment.development';
import { InvoiceServiceService } from '../../services/invoice-service.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
  standalone: false,
})
export class DashboardComponent implements OnInit {
  apiUrl = environment.apiUrl;
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
    id: 0,
  };

  companyNames: string[] = [];
  clientsLoaded = false;

  invoices: iInvoices[] = [];

  constructor(
    private http: HttpClient,
    private clientService: ClientServiceService,
    private authService: AuthServiceService,
    private invoiceService: InvoiceServiceService
  ) {}

  ngOnInit(): void {
    this.getRoleFromToken();
  }

  fetchAllClients() {
    if (this.clientsLoaded) {
      this.clientsLoaded = false;
      this.companyNames = [];
    } else {
      this.clientService.getAllClients().subscribe(
        (response) => {
          this.companyNames = response.content.map(
            (client) => client.companyName
          );
          this.clientsLoaded = true;
          console.log('Nomi delle aziende:', this.companyNames);
        },
        (error) => {
          console.error('Errore durante il recupero delle aziende:', error);
          alert('Errore durante il recupero delle aziende.');
        }
      );
    }
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

  fetchAllInvoices() {
    this.invoiceService.getAllInvoices().subscribe(
      (invoices) => {
        this.invoices = invoices;
        console.log('Fatture recuperate:', this.invoices);
      },
      (error) => {
        console.error('Errore durante il recupero delle fatture:', error);
        alert('Errore durante il recupero delle fatture.');
      }
    );
  }

  deleteInvoice(id: number) {
    this.invoiceService.deleteInvoiceById(id).subscribe(
      () => {
        this.invoices = this.invoices.filter((invoice) => invoice.id !== id);
        console.log(`Fattura con ID ${id} eliminata con successo.`);
      },
      (error) => {
        console.error(
          `Errore durante l'eliminazione della fattura con ID ${id}:`,
          error
        );
        alert("Errore durante l'eliminazione della fattura.");
      }
    );
  }
}
