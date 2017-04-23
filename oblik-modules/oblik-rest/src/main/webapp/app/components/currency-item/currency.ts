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
    private currencyService: CurrencyService;

    constructor(private currencyService: CurrencyService) {
        this.currencyModel = new FormGroup({
            'symbol': new FormControl('', [Validators.required, Validators.maxLength(10)]),
            'rate': new FormControl('', [Validators.required, Validators.pattern(/^\d+\.?\d*$/)])
        });
        this.currencyService = currencyService;
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

    }

    public remove() {
        this.currencyService.remove(this.currency.id);
    }
}
