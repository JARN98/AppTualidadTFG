import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OneNoticiaService } from 'src/app/services/one-noticia.service';
import { ListaNoticiasService } from 'src/app/services/lista-noticias.service';
import { EditNoticiaDto } from 'src/app/models/edit-noticia.dto';

@Component({
  selector: 'app-edit-noticia',
  templateUrl: './edit-noticia.component.html',
  styleUrls: ['./edit-noticia.component.css']
})
export class EditNoticiaComponent implements OnInit {
  public form: FormGroup;
  edit: boolean;
  editNoticiaDto: EditNoticiaDto;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private oneNoticiaService: OneNoticiaService,
    private listaNoticiasService: ListaNoticiasService,
    public dialogRef: MatDialogRef<EditNoticiaComponent>) { }

  ngOnInit() {
    console.log(this.data);

    if (this.data === undefined) {

      this.form = this.fb.group({
        title: [null, Validators.compose([Validators.required])],
        descripcion: [null, Validators.compose([Validators.required])]
      });
      this.edit = false;
    } else {
      this.getOneNoticia();
      this.edit = true;
    }
  }

  getOneNoticia() {
    this.oneNoticiaService.getOneNoticias(this.data.noticia.id).subscribe(noticia => {
      this.form = this.fb.group({
        title: [noticia.title, Validators.compose([Validators.required])],
        descripcion: [noticia.description, Validators.compose([Validators.required])]
      });
    }, err => err);
  }

  editNoticia() {
    this.editNoticiaDto = new EditNoticiaDto(this.form.controls['title'].value, this.form.controls['descripcion'].value);

    this.listaNoticiasService.editNoticia(this.editNoticiaDto, this.data.noticia.id).subscribe(noticia => {
      this.dialogRef.close();
    }, err => err);
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
