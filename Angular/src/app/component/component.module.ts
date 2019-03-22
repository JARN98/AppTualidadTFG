import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuarioListComponent } from './usuario-list/usuario-list.component';
import { NoticiasComponent } from './noticias/noticias.component';
import { RouterModule } from '@angular/router';
import { RoustesComponents } from './component-routing.module';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material';
import { DetalleNoticiaComponent } from './detalle-noticia/detalle-noticia.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [UsuarioListComponent, NoticiasComponent, DetalleNoticiaComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(RoustesComponents),
    MatTableModule,
    MatPaginatorModule,
    FlexLayoutModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    NgbModule
  ]
})
export class ComponentModule { }
