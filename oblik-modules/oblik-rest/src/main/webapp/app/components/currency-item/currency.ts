import {Component, Input} from "@angular/core";
import {Currency} from "../../services/currency-service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

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
            'symbol': new FormControl('', [Validators.required, Validators.maxLength(10)]),
            'rate': new FormControl('', [Validators.required, Validators.pattern(/^\d+\.?\d*$/)])
        });
    }

    public toggleEditing() {
        this.editing = !this.editing;
        if (this.currency != null) {
            this.currencyModel.controls['symbol']
                .setValue(this.currency.symbol);
            this.currencyModel.controls['rate']
                .setValue(this.currency.defaultRate ? 1 : this.currency.rate);
        }
    }

    public onSubmit() {
        console.log(this.currencyModel.value);
        console.log(this.currencyModel.valid);
        console.log(this.currency)
    }

}
