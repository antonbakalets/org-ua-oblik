export class LoginAccount {
  username: string;
  password: string;
  captcha: string;
}

export class TokenAccount {
  expiration: Date;
  token: string;
}
