import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {routing} from './app.routing';

import {AppComponent} from './app.component';
import {AuthGuard} from './guards/auth.guard';
import {BudgetComponent} from './budget/budget.component';
import {LoginComponent} from './login/login.component';
import {AuthService} from './auth/auth.service';
import {TokenInterceptor} from './auth/token.interceptor';
import {BudgetService} from './budget/budget.service';


@NgModule({
  declarations: [
    AppComponent,
    BudgetComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    routing
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    AuthGuard,
    AuthService,
    BudgetService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
