import { Component, OnInit, NgZone } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { OneNoticiaService } from 'src/app/services/one-noticia.service';
import { NoticiaResponse } from 'src/app/responses/noticia.response';
import { Photo } from 'src/app/models/photo';
import { MatDialog } from '@angular/material';
import { EditPhotosComponent } from 'src/app/dialogs/edit-photos/edit-photos.component';
import { UploadImageDetailsDto } from 'src/app/models/upload-image-details.dto';

@Component({
  selector: 'app-detalle-noticia',
  templateUrl: './detalle-noticia.component.html',
  styleUrls: ['./detalle-noticia.component.css']
})
export class DetalleNoticiaComponent implements OnInit {
  public form: FormGroup;
  private idNoticia: String;
  public noticiaResponse: NoticiaResponse;
  public photos: Photo[];

  constructor(private fb: FormBuilder,
    private route: ActivatedRoute,
    private ngZone: NgZone,
    public dialog: MatDialog,
    private oneNoticiaService: OneNoticiaService) {
    this.route.queryParams.subscribe(params => {
      this.idNoticia = params['id'];
    });
  }

  ngOnInit() {
    this.getOneNew();
  }

  openDialogEditNoticia() {
    const dialogoEditNoticia = this.dialog.open(DetalleNoticiaComponent, {
      data: {
        noticia: this.noticiaResponse,
      },
      width: '60%',
      height: '60%'
    });
    dialogoEditNoticia.afterClosed().subscribe(result => {
      this.getOneNew();
    });
  }

  getOneNew() {
    this.oneNoticiaService.getOneNoticias(this.idNoticia).subscribe(noticia => {
      this.noticiaResponse = noticia;
      this.photos = noticia.photos;
      console.log(this.photos);
    }, err => err);
  }

  eliminarComentario(id) {
    this.oneNoticiaService.eliminarComentarioAdmin(id).subscribe(comentario => {
      this.ngZone.run(() => this.getOneNew());
    }, err => err);
  }

  openDialogEditPhotos() {
    const dialogoEditPhoto = this.dialog.open(EditPhotosComponent, {
      data: {
        id: this.idNoticia,
      },
      width: '80%',
      height: '80%'
    });
    dialogoEditPhoto.afterClosed().subscribe(result => {
      this.getOneNew();
    });
  }


}
