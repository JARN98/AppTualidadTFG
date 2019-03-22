import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AppComponent } from './app.component';
import { NavbarComponent } from './core/navbar/navbar.component';
import { NavbarModule, WavesModule } from 'angular-bootstrap-md';
import { Approutes } from './app-routing.module';
import { Routes, RouterModule } from '@angular/router';
import { FullComponent } from './layouts/full/full.component';
import { HttpClientModule } from '@angular/common/http';
import {MatFormFieldModule} from '@angular/material/form-field';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatInputModule} from '@angular/material/input';
import {MatTableModule} from '@angular/material/table';
import { DeleteNoticiaComponent } from './dialogs/delete-noticia/delete-noticia.component';
import {MatDialogModule, MAT_DIALOG_DEFAULT_OPTIONS} from '@angular/material/dialog';
import { EditNoticiaComponent } from './dialogs/edit-noticia/edit-noticia.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { EditPhotosComponent } from './dialogs/edit-photos/edit-photos.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FullComponent,
    DeleteNoticiaComponent,
    EditNoticiaComponent,
    EditPhotosComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(Approutes),
    NavbarModule,
    WavesModule,
    HttpClientModule,
    MatFormFieldModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatTableModule,
    MatDialogModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule.forRoot()
  ],
  providers: [
    { provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: { hasBackdrop: true } }
  ],
  entryComponents: [
    DeleteNoticiaComponent,
    EditNoticiaComponent,
    EditPhotosComponent
  ],
  bootstrap: [AppComponent],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
]
})
export class AppModule { }
