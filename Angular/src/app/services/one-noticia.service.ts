import { Injectable } from '@angular/core';
import { DashboardService } from './dashboard.service';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NoticiaResponse } from '../responses/noticia.response';
import { environment } from 'src/environments/environment';
import { UploadImageDto } from '../models/upload-image.dto';
import { Photo } from '../models/photo';

@Injectable({
  providedIn: 'root'
})
export class OneNoticiaService {

  constructor(private http: HttpClient,
    private dashboardService: DashboardService) { }


  getOneNoticias(idNoticia: String): Observable<NoticiaResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.dashboardService.getToken()}`,
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.get<NoticiaResponse>(`${environment.ApiUrl}noticias/${idNoticia}`, requestOptions);
  }

  eliminarComentarioAdmin(idComentario: String): Observable<any> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.dashboardService.getToken()}`,
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.delete<any>(`${environment.ApiUrl}comentarios/admin/${idComentario}`, requestOptions);
  }

  eliminarImagen(idImagen: String): Observable<any> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.dashboardService.getToken()}`,
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.http.delete<any>(`${environment.ApiUrl}photos/${idImagen}`, requestOptions);
  }

  subirFoto(uploadImageDto: UploadImageDto): Observable<Photo> {
    console.log(uploadImageDto);
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded',
        'Authorization': `Bearer ${this.dashboardService.getToken()}`,
        'Access-Control-Allow-Origin': '*'
      })
    };

    class Uno {
      photo: File;
      noticia: String;

      constructor(photo: File, noticia: String) {
        this.photo = photo;
        this.noticia = noticia;
      }
    }
    const uno = new Uno(uploadImageDto.photo, uploadImageDto.noticia);
    return this.http.post<Photo>(`${environment.ApiUrl}/photos`, uno, requestOptions);
  }
}
