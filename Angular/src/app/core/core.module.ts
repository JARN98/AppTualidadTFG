import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { NavbarModule, WavesModule } from 'angular-bootstrap-md';

@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    NavbarModule,
    WavesModule
  ],
  providers: [],
  bootstrap: [NavbarComponent]
})
export class CoreModule { }
