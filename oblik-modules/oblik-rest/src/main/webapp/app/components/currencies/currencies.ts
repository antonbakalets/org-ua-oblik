import {Component} from "@angular/core";
import {Currency, CurrencyService} from "../../services/currency-service";

@Component({
    selector: 'oblik-currencies',
    templateUrl: 'app/components/currencies/currencies.html'
})
export default class CurrenciesComponent {
    currencies: Currency[];

    constructor(private currencyService: CurrencyService) {
        this.currencies = currencyService.list();
    }

    public add() {
        this.currencies.push(new Currency(null, null, null, false, null, true, "DDD"));
    }
}
