import {Component, Input} from "@angular/core";
import {Currency, CurrencyService} from "../../services/currency-service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
    selector: 'oblik-currency-item',
    templateUrl: 'app/components/currency-item/currency.html'
})
export default class CurrencyItemComponent {
    @Input() currency: Currency;

    private editing: boolean = false;
    private currencyModel: FormGroup;

    constructor(private currencyService: CurrencyService) {
        this.currencyModel = new FormGroup({
            'symbol': new FormControl('', [Validators.required, Validators.maxLength(10)]),
            // TODO add symbol already exist validation
            'rate': new FormControl('1', [Validators.required, Validators.pattern("^[0-9]+\.?[0-9]*$")])
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
        console.log(this.currencyModel);
        console.log(this.currency);
        if (this.currencyModel.valid) {
            if (this.currency == null) {
                this.currencyService.insert(
                    this.currencyModel.get('symbol').value,
                    this.currencyModel.get('rate').value);
            } else {
                this.currencyService.update(
                    this.currency.id,
                    this.currencyModel.get('symbol').value,
                    this.currencyModel.get('rate').value);
            }
            this.toggleEditing();
        }
    }

    public remove() {
        this.currencyService.remove(this.currency.id);
    }
}
