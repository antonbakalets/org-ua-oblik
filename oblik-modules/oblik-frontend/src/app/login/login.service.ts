import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {LoginAccount, TokenAccount} from './login-account';

@Injectable()
export class LoginService {

  constructor(private http: HttpClient) {
  }

  signIn(account: LoginAccount) {
    this.http.post<TokenAccount>('auth/authenticate', account)
      .subscribe((data) => {
        localStorage.setItem('jwtToken', data.token);
      }, (error) => {
        console.log(error);
      });
  }
}
