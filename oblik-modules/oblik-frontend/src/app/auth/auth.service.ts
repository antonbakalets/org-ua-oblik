import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {LoginAccount, TokenAccount} from '../login/login-account';

@Injectable()
export class AuthService {

  constructor(private http: HttpClient) {
  }

  getToken(): string {
    return localStorage.getItem('jwtToken');
  }

  signIn(account: LoginAccount) {
    this.http.post<TokenAccount>('auth/authenticate', account)
      .subscribe((data) => {
        localStorage.setItem('jwtToken', data.token);
      }, (error) => {
        console.log(error);
      });
  }

  register(user: string) {
    return this.http.post('auth/register', user);
  }

  forgot(email: string) {
    return this.http.post('auth/forgot', email);
  }
}
