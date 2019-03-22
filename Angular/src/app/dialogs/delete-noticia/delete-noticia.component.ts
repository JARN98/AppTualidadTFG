import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { ListaNoticiasService } from 'src/app/services/lista-noticias.service';

@Component({
  selector: 'app-delete-noticia',
  templateUrl: './delete-noticia.component.html',
  styleUrls: ['./delete-noticia.component.css']
})
export class DeleteNoticiaComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<DeleteNoticiaComponent>,
    private listaNoticiasService: ListaNoticiasService) { }

  ngOnInit() {
  }

  closeDialog() {
    this.dialogRef.close();
  }

  deleteNoticia() {
    console.log(this.data);

    this.listaNoticiasService.eliminarNoticia(this.data.id).subscribe(() => {

      this.closeDialog();

    }, err => err);

  }

}
