import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import ApplicationComponent from "./components/application/application";
import TransactionsComponent from "./components/transactions/transactions";
import {ProductService} from "./services/product-service";
import {CurrencyService} from "./services/currency-service";
import CurrenciesComponent from "./components/currencies/currencies";
import CurrencyItemComponent from "./components/currencies/currency";
import AccountsComponent from "./components/accounts/accounts";

@NgModule({
    imports: [BrowserModule],
    declarations: [ApplicationComponent,
        AccountsComponent,
        CurrenciesComponent,
        CurrencyItemComponent,
        TransactionsComponent],
    providers: [ProductService,
        CurrencyService],
    bootstrap: [ApplicationComponent]
})
export class AppModule {
}
