import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ReactiveFormsModule} from "@angular/forms";

import ApplicationComponent from "./components/application/application";
import TransactionsComponent from "./components/transactions/transactions";
import {ProductService} from "./services/product-service";
import {CurrencyService} from "./services/currency-service";
import {AccountService} from "./services/account-service";
import CurrenciesComponent from "./components/currencies/currencies";
import CurrencyItemComponent from "./components/currency-item/currency";
import AccountsComponent from "./components/accounts/accounts";
import AccountItemComponent from "./components/account-item/account";

@NgModule({
    imports: [BrowserModule, ReactiveFormsModule],
    declarations: [ApplicationComponent,
        AccountsComponent,
        AccountItemComponent,
        CurrenciesComponent,
        CurrencyItemComponent,
        TransactionsComponent],
    providers: [ProductService,
        CurrencyService,
        AccountService],
    bootstrap: [ApplicationComponent]
})
export class AppModule {
}
