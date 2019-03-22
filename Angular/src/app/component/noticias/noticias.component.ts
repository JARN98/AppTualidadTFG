import { Component, OnInit } from '@angular/core';
import { ListaNoticiasService } from 'src/app/services/lista-noticias.service';
import { ListResponse } from 'src/app/responses/list.response';
import { NoticiaResResponse } from 'src/app/responses/noticiaRes.respose';
import { MatDialog } from '@angular/material';
import { DeleteNoticiaComponent } from 'src/app/dialogs/delete-noticia/delete-noticia.component';
import { EditNoticiaComponent } from 'src/app/dialogs/edit-noticia/edit-noticia.component';
import {NavigationExtras, Router} from '@angular/router';

@Component({
  selector: 'app-noticias',
  templateUrl: './noticias.component.html',
  styleUrls: ['./noticias.component.scss']
})
export class NoticiasComponent implements OnInit {
  listaApi: ListResponse;
  listaNoticias: NoticiaResResponse[];
  constructor(
    private listaNoticiasService: ListaNoticiasService,
    public dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit() {
    this.getListNoticias();

  }

  getListNoticias() {
    this.listaNoticiasService.listNoticias().subscribe(noticias => {
      this.listaApi = noticias;
      this.listaNoticias = this.listaApi.rows;

    }, err => err);
  }

  openDialogDeleteNoticia(noticia) {
    const dialogoDeleteNoticia = this.dialog.open(DeleteNoticiaComponent, {
      data: {
        id: noticia
      }
    });
    dialogoDeleteNoticia.afterClosed().subscribe(result => {
      this.getListNoticias();
    });
  }

  openDialogEditNoticia(noticia) {
    const dialogoEditNoticia = this.dialog.open(EditNoticiaComponent, {
      data: {
        noticia: noticia,
      },
      width: '60%',
      height: '60%'
    });
    dialogoEditNoticia.afterClosed().subscribe(result => {
      this.getListNoticias();
    });
  }

  getPhoto(noticia) {
    const photo = noticia.photos;
    if (photo === undefined) {
      return '';
    } else {
      return photo.link;
    }


  }

  public detalles(id) {
    const navigationExtras: NavigationExtras = {
        queryParams: {
            'id': id
        }
    };
    this.router.navigate(['/component/noticia'], navigationExtras);
}






}
