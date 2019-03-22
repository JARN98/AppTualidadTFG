import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NavbarComponent } from './core/navbar/navbar.component';
import { FullComponent } from './layouts/full/full.component';
import { AuthGuardService as AuthGuard } from './services/auth-guard.service';


export const Approutes: Routes = [
  {
    path: 'component',
    component: FullComponent,
    children: [
      {
        path: '',
        loadChildren: './component/component.module#ComponentModule',
        canActivate: [AuthGuard]
      }
    ]
  },
  {
    path: 'session',
    children: [
      {
        path: '',
        loadChildren: './session/session.module#SessionModule'
      }
    ]
  },
  { path: '', redirectTo: '/session/login', pathMatch: 'full' },
];
