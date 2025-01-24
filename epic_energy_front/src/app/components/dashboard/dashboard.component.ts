import { iInvoices } from './../../interfaces/invoices';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ResponseBody } from '../../interfaces/all-clients-response';
import { ClientServiceService } from '../../services/client-service.service';
import { AuthServiceService } from '../../services/auth-service.service';
import { environment } from '../../../environments/environment.development';
import { UserServiceService } from '../../services/user-service.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
  standalone: false,
})
export class DashboardComponent implements OnInit {
  apiUrl = environment.apiUrl;
  loading: boolean = true;

  // Basic user & token info
  username: string = '';
  role: string = 'USER';
  isAdmin: boolean = false;
  user: any | null = null;

  clients: ResponseBody | null = null;
  mood: string = 'reading';
  name: any;
  surname: any;
  selectedItem: string = 'dashboard';

  // Profile Picture Handling
  profilePictureUrl: string | ArrayBuffer | null = null; // URL for <img src=...>
  defaultImageUrl: string =
    'https://images.immediate.co.uk/production/volatile/sites/3/2024/08/squid-game-season-2-b7cbb06.jpg';
  uploadFile: File | null = null; // stores user-selected file from <input type="file">

  showModal: boolean = false;

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
    private authService: AuthServiceService,
    private userService: UserServiceService
  ) {}

  ngOnInit(): void {
    this.getRoleFromToken();
    this.getUserDataFromToken();
  }

  fetchUser(username: string) {
    this.userService.getUser(username).subscribe({
      next: (response: any) => {
        this.user = response;
        console.log(response);
        this.loadProfilePicture(response.id);
      },
      error: (error) => {
        console.log('Error' + error);
      },
    });
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

  getUserDataFromToken(): any {
    const token = this.authService.getToken();
    if (!token) {
      return null;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      console.log(payload);

      this.username = payload.sub;
      this.fetchUser(payload.sub);
      return payload;
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
        (response: iInvoices) => {
          alert('Fattura creata con successo!');
          this.mood = 'reading';
          console.log('Fattura creata:', response);
        },
        (error: any) => {
          console.error(error);
          alert('Errore durante la creazione della fattura.');
        }
      );
  }

  // 1. Load the profile picture from backend
  loadProfilePicture(userId: number): void {
    this.userService.getProfilePicture(userId).subscribe({
      next: (blob: Blob) => {
        // Convert the blob to a local object URL for display
        this.profilePictureUrl = URL.createObjectURL(blob);
        this.loading = false;
      },
      error: (error) => {
        // If the user has no profile pic or something went wrong,
        // fallback to the default URL
        this.loading = false;
        console.error('Profile picture not found, using default:', error);
        this.profilePictureUrl = null; // triggers fallback to default in template
      },
    });
  }

  // 2. Open/close the modal for a bigger view & change
  openModal(): void {
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.uploadFile = null; // reset file input
  }

  // 3. Store the file user selected
  onFileSelected(event: any, userId: number): void {
    const file: File = event.target.files[0];
    if (file) {
      this.uploadFile = file;
      const reader = new FileReader();
      reader.onload = (e) => (this.profilePictureUrl = reader.result);
      reader.readAsDataURL(file);

      if (!this.user || !this.user.id || !this.uploadFile) return;

      // 1. Upload to backend
      this.userService.uploadProfilePicture(userId, this.uploadFile).subscribe({
        next: () => {
          console.log('Picture uploaded successfully!');
          this.closeModal();
        },
        error: (error) => {
          console.error('Error uploading picture:', error);
        },
      });
    }
  }
}
