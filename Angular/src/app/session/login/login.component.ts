import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import { LoginDto } from 'src/app/models/login.dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string;
  password: string;
  error: boolean;

  constructor(
    private loginService: SessionService,
    private router: Router,
  ) { }

  ngOnInit() {
  }


  doLogin() {
    if ( this.email === '' || this.password === '') {
      this.error = true;
      return this.error;
    }
    const loginDto = new LoginDto(this.email, this.password);
    console.log(loginDto);

    this.loginService.login(loginDto).subscribe(response => {
      localStorage.setItem('token', response.token);
      this.router.navigate(['/component/listaNoticias']);
    }, err => {
      return err;
    });
  }

}
