import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { UsuariosResponse } from 'src/app/responses/usuario.response';
import { ListResponse } from 'src/app/responses/list.response';
import { DashboardService } from 'src/app/services/dashboard.service';
import { User } from 'src/app/responses/login.respose';

@Component({
  selector: 'app-usuario-list',
  templateUrl: './usuario-list.component.html',
  styleUrls: ['./usuario-list.component.css']
})
export class UsuarioListComponent implements OnInit {
  listaApi: ListResponse;
  listaUsuariosRes: UsuariosResponse[];
  displayedColumns: string[] = ['username', 'email', 'acciones'];
  dataSource = new MatTableDataSource<UsuariosResponse[]>();
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private listUsuarioService: DashboardService) { }

  ngOnInit() {
    this.listUser();
  }


  listUser() {
    this.listUsuarioService.listUsuarios().subscribe(lista => {
      this.listaApi = lista;
      this.listaUsuariosRes = this.listaApi.rows;
      this.dataSource = new MatTableDataSource<UsuariosResponse[]>(this.listaApi.rows);
      this.dataSource.paginator = this.paginator;
      // this.dataSource = lista;
      console.log(this.listaUsuariosRes);

    }, error => {
      console.error(error);
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  deleteUsuario(element: User) {
    console.log(element.id);

    this.listUsuarioService.deleteUsuario(element).subscribe(resp => {

      console.log(resp);
      this.listUser();
    }, error => console.error(error)
    );
  }

}
