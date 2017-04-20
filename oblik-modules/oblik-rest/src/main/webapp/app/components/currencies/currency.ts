import {Component, Input} from "@angular/core";
import {Currency} from "../../services/product-service";

@Component({
    selector: 'oblik-currency-item',
    templateUrl: 'app/components/currencies/currency.html'
})
export default class CurrencyItemComponent {
    @Input() currency: Currency;
}
