import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DashboardService } from './dashboard.service';
import { environment } from 'src/environments/environment';
import { ListResponse } from '../responses/list.response';
import { NoticiaResponse } from '../responses/noticia.response';
import { EditNoticiaDto } from '../models/edit-noticia.dto';

@Injectable({
  providedIn: 'root'
})
export class ListaNoticiasService {

  constructor(private http: HttpClient,
    private dashboardService: DashboardService) { }

  listNoticias(): Observable<ListResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.dashboardService.getToken()}`,
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.get<ListResponse>(`${environment.ApiUrl}/noticias`, requestOptions);
  }

  eliminarNoticia(idNoticia: String): Observable<any> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.dashboardService.getToken()}`,
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.delete<any>(`${environment.ApiUrl}noticias/${idNoticia}`, requestOptions);
  }


  editNoticia(editNoticia: EditNoticiaDto, idNoticia: String): Observable<NoticiaResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.dashboardService.getToken()}`,
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.put<NoticiaResponse>(`${environment.ApiUrl}/noticias/${idNoticia}`, editNoticia, requestOptions);
  }
}
