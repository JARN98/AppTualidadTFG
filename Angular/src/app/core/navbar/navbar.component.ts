import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  opcion = 1;
  constructor(private router: Router) { }

  ngOnInit() {
    this.opcion = 1;
  }

  navegarUsuarios() {
    this.opcion = 1;
    this.router.navigate(['/component/listaUsuarios']);
  }

  navegarNoticias() {
    this.opcion = 2;
    this.router.navigate(['/component/listaNoticias']);
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/session/login']);
  }

}
