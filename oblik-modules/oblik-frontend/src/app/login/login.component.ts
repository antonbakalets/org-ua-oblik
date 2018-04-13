import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginService} from './login.service';
import {LoginAccount} from './login-account';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  targetUrl = '';
  loginAccount: LoginAccount;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private loginService: LoginService) {
  }

  ngOnInit() {
    this.route.queryParams
      .subscribe(params => this.targetUrl = params['return'] || '/');
  }

  login() {
    this.loginAccount = new LoginAccount();
    this.loginAccount.username = 'paco';
    this.loginAccount.password = 'hola';

    this.loginService.signIn(this.loginAccount);

    this.router.navigate([this.targetUrl]);
  }
}
