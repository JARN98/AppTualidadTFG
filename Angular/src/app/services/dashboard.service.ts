import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ListResponse } from '../responses/list.response';
import { User } from '../responses/login.respose';

declare var require: any;
const jwtDecode = require('jwt-decode');

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient) { }

  listUsuarios(): Observable<ListResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.getToken()}`,
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.get<ListResponse>(`${environment.ApiUrl}/users`, requestOptions);
  }


  deleteUsuario(usuario: User): Observable<User> {
    console.log(usuario.id);

    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.getToken()}`,
        'Access-Control-Allow-Origin': '*'
      })
    };

    return this.http.delete<User>(`${environment.ApiUrl}/users/${usuario.id}`, requestOptions);
  }

  getToken() {

    return localStorage.getItem('token');
  }

  getTokenDecode() {
    if (!(this.getToken() == null)) {
      return jwtDecode(this.getToken());
    } else {
      return null;
    }
  }
}
