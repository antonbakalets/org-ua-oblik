import {Component, Input} from "@angular/core";
import {Currency} from "../../services/currency-service";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
    selector: 'oblik-currency-item',
    templateUrl: 'app/components/currency-item/currency.html'
})
export default class CurrencyItemComponent {
    @Input() currency: Currency;

    private editing: boolean = false;
    private currencyModel: FormGroup;

    constructor() {
        this.currencyModel = new FormGroup({
            'symbol': new FormControl(),
            'rate': new FormControl()
        });
    }

    public toggleEditing() {
        this.editing = !this.editing;
        this.currencyModel.controls['symbol']
            .setValue(this.currency.symbol);
        this.currencyModel.controls['rate']
            .setValue(this.currency.defaultRate ? 1 : this.currency.rate);
    }

    public onSubmit() {
        console.log(this.currencyModel.value);
    }

}
