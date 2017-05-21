import {Component, Input} from "@angular/core";
import {Currency, CurrencyService} from "../../services/currency-service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
    selector: 'oblik-currency-item',
    templateUrl: 'app/components/currency-item/currency.html',
    styleUrls: ['app/components/currency-item/currency.css']
})
export default class CurrencyItemComponent {
    @Input() currency: Currency;

    private editing: boolean = false;
    private currencyModel: FormGroup;

    constructor(private currencyService: CurrencyService) {
    }

    ngOnInit(): void {
        this.currencyModel = new FormGroup({
            'symbol': new FormControl('', [Validators.required, Validators.maxLength(10)]),
            // TODO add symbol already exist validation
            'rate': new FormControl('1', [Validators.required, Validators.pattern("^[0-9]+\.?[0-9]*$")])
        });

        this.currencyModel.valueChanges.subscribe(data => this.onValueChanged(data));

        this.onValueChanged(); // (re)set validation messages now
    }

    onValueChanged(data?: any) {
        if (!this.currencyModel) {
            return;
        }
        const form = this.currencyModel;
        for (const field in this.formErrors) {
            // clear previous error message (if any)
            this.formErrors[field] = '';
            const control = form.get(field);
            if (control && control.dirty && !control.valid) {
                const messages = this.validationMessages[field];
                for (const key in control.errors) {
                    this.formErrors[field] += messages[key] + ' ';
                }
            }
        }
    }

    formErrors = {
        'symbol': '',
        'rate': ''
    };

    validationMessages = {
        'symbol': {
            'required':     'Symbol is required.',
            'exist':        'Symbol already exist.',
            'maxlength':    'Currency symbol cannot be more than 10 characters long.'
        },
        'rate': {
            'required':     'Rate is required.',
            'pattern':      'Decimal only.'
        }
    };

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
        // console.log(this.currencyModel);
        // console.log(this.currency);
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
