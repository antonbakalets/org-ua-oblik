import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {routing} from './app.routing';

import {AppComponent} from './app.component';
import {AuthGuard} from './guards/auth.guard';
import {BudgetComponent} from './budget/budget.component';
import {LoginComponent} from './login/login.component';
import {LoginService} from './login/login.service';


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
    AuthGuard,
    LoginService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
