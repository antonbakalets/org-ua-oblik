import {Component, Input} from "@angular/core";
import {Currency} from "../../services/currency-service";

@Component({
    selector: 'oblik-currency-item',
    templateUrl: 'app/components/currency-item/currency.html'
})
export default class CurrencyItemComponent {
    @Input() currency: Currency;
}
