import {Routes, RouterModule} from '@angular/router';

import {BudgetComponent} from './budget/budget.component';
// import { LoginComponent } from './login/index';
// import { RegisterComponent } from './register/index';
import {AuthGuard} from './guards/auth.guard';
import {LoginComponent} from './login/login.component';

const appRoutes: Routes = [
  {path: '', component: BudgetComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  // { path: 'register', component: RegisterComponent },

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);
