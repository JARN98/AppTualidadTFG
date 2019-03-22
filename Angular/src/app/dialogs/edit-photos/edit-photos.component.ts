import { Component, OnInit, Inject, NgZone } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { OneNoticiaService } from 'src/app/services/one-noticia.service';
import { UploadImageDetailsDto } from 'src/app/models/upload-image-details.dto';
import { UploadImageDto } from 'src/app/models/upload-image.dto';
import { Photo } from 'src/app/models/photo';

@Component({
  selector: 'app-edit-photos',
  templateUrl: './edit-photos.component.html',
  styleUrls: ['./edit-photos.component.css']
})
export class EditPhotosComponent implements OnInit {
  photos: Photo[];
  uploadImageDetailsDto: UploadImageDetailsDto;
  dtoImagenUpload: UploadImageDto;
  imagenesNoticia: String[];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    private oneNoticiaService: OneNoticiaService,
    private ngZone: NgZone, ) { }

  ngOnInit() {
    this.getOneNew();
  }

  getOneNew() {
    this.oneNoticiaService.getOneNoticias(this.data.id).subscribe(noticia => {
      this.photos = noticia.photos;
      console.log(this.photos);
    }, err => err);
  }

  eliminarImagen(id) {

    this.oneNoticiaService.eliminarImagen(id.id).subscribe(imagen => {
      this.ngZone.run(() => this.getOneNew());
    }, err => err);
  }

}
