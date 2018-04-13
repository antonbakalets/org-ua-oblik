import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';

import {LoginAccount} from './login-account';

@Injectable()
export class LoginService {

  constructor(private http: HttpClient) {
  }

  signIn(account: LoginAccount): boolean {
    let b: boolean;
    this.http.post('api/auth', account)
      .subscribe((response) => {
        console.log(response);
        b = true;
      }, (error) => {
        console.log(error);
        b = false;
      });
    return b;
  }
}
