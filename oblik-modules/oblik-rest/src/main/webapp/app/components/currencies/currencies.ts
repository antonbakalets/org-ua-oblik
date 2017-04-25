import {Component} from "@angular/core";
import {Currency, CurrencyService} from "../../services/currency-service";

@Component({
    selector: 'oblik-currencies',
    templateUrl: 'app/components/currencies/currencies.html',
    styleUrls: ['app/components/currencies/currencies.css']
})
export default class CurrenciesComponent {
    currencies: Currency[];

    constructor(private currencyService: CurrencyService) {
        this.currencies = currencyService.list();
        this.currencies.push(null);
    }
}
