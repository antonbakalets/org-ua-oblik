import {Component} from "@angular/core";
import {Currency, ProductService} from "../../services/product-service";

@Component({
    selector: 'oblik-currencies',
    templateUrl: 'app/components/currencies/currencies.html'
})
export default class CurrenciesComponent {
    currencies: Currency[] = [];

    constructor(private productService: ProductService) {
        this.currencies = productService.getCurrencies();
    }
}
