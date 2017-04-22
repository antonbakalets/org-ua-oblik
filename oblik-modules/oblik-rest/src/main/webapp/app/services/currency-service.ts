export class Currency {
    public editing: boolean = false;

    constructor(public id: number,
                public rate: number,
                public symbol: String,
                public defaultRate: boolean,
                public total: number,
                public removable: boolean,
                public defaultSymbol: String) {
        this.rate = this.defaultRate ? 1 : rate;
        this.editing = (id == null);
    }


}

export class CurrencyService {
    list(): Array<Currency> {
        let currencies: Array<Currency> = new Array<Currency>();
        currencies.push(new Currency(1, 90, "grn", false, 91, false, "DDD"));
        currencies.push(new Currency(1, 80, "pln", true, 81, false, "DDD"));
        return currencies;
    }

    save(currency: Currency) {
        if (currency.id == null) {
            this.insert(currency);
        } else {
            this.update(currency);
        }
    }
    
    private insert(currency: Currency) {
        console.log("Inserting " + currency.symbol);
    }

    private update(currency: Currency) {
        console.log("Updating" + currency.symbol);
    }
}