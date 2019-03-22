import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { DashboardService } from './dashboard.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(public router: Router,
    private dashboardService: DashboardService) { }

  canActivate(): boolean {
    if (this.dashboardService.getToken === undefined) {
      this.router.navigate(['session']);
      return false;
    }
    return true;
  }
}
