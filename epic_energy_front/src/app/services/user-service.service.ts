import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {
  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getUser(username: string) {
    return this.http.get(this.apiUrl + '/users/' + username);
  }

  // 1. Upload profile picture
  uploadProfilePicture(id: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(`${this.apiUrl}/users/${id}/profile-picture`, formData);
  }

  // 2. Get profile picture (returns raw binary data as a Blob)
  getProfilePicture(id: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/users/${id}/profile-picture`, {
      responseType: 'blob',
    });
  }
}
