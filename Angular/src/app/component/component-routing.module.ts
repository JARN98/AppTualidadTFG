import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsuarioListComponent } from './usuario-list/usuario-list.component';
import { NoticiasComponent } from './noticias/noticias.component';
import { DetalleNoticiaComponent } from './detalle-noticia/detalle-noticia.component';

export const RoustesComponents: Routes = [
  {
    path: '',
    children: [
      {
        path: 'listaUsuarios',
        component: UsuarioListComponent
      },
      {
        path: 'listaNoticias',
        component: NoticiasComponent
      },
      {
        path: 'noticia',
        component: DetalleNoticiaComponent
      },
      { path: '**', component: NoticiasComponent}
    ]
  }
];

