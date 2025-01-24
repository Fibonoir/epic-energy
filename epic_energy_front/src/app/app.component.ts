import { AuthServiceService } from './services/auth-service.service';
import { Component, HostListener, OnInit } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrl: './app.component.css',
    standalone: false
})
export class AppComponent implements OnInit {
  title = 'epic_energy_front';
  private currentUrl: string | null = null;


  constructor(private router: Router, private cookieService: CookieService, private authService: AuthServiceService) {}

  ngOnInit(): void {
    this.currentUrl = this.router.url;

    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        if (this.currentUrl !== '/dashboard' && !event.url.startsWith('/dashboard')) {
          this.authService.clearToken();
        }
        this.currentUrl = event.url; // Update current URL

      }
    });
  }


    @HostListener('window:beforeunload', ['$event'])
    handleTabClose(event: Event): void {
      if (this.currentUrl !== '/dashboard') {
        this.authService.clearToken();
      }
    }
}
