export class Currency {
    constructor(public id: number,
                public total: number,
                public symbol: String,
                public defaultRate: boolean,
                public rate: number,
                public removable: boolean,
                public defaultSymbol: String) {
    }
}

export class CurrencyService {
    // TODO merge
    private currenciesMap: Map<number, Currency> = new Map<number, Currency>();
    private currenciesArray: Array<Currency> = new Array<Currency>();

    constructor() {
        this.currenciesArray.push(new Currency(1, 1234.5, "грн.", true, 1, false, "грн."));
        this.currenciesArray.push(new Currency(2, 2340, "$", false, 26.85, true, "грн."));
        this.currenciesArray.forEach(c => this.currenciesMap.set(c.id, c));
    }

    list(): Array<Currency> {
        return this.currenciesArray;
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

    remove(id: number) {
        console.log("Removing" + id);
        this.currenciesMap.delete(id);
    }
}