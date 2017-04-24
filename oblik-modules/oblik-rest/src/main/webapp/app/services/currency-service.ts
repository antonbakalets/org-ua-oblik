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
    private first: Currency;
    // TODO merge
    private currenciesMap: Map<number, Currency> = new Map<number, Currency>();
    private currenciesArray: Array<Currency> = new Array<Currency>();

    constructor() {
        this.first = new Currency(0, 1234.5, "грн.", true, 1, false, "грн.");
        this.currenciesArray.push(this.first);
        this.currenciesArray.push(new Currency(1, 2340, "$", false, 26.85, true, this.first.symbol));
        this.currenciesArray.push(new Currency(2, 456, "євро", false, 26.85, true, this.first.symbol));
        this.currenciesArray.forEach(c => this.currenciesMap.set(c.id, c));
    }

    list(): Array<Currency> {
        return this.currenciesArray;
    }

    insert(symbol: string, rate: number): Currency {
        console.log("Inserting currency " + symbol);
        let id = this.currenciesArray.length;
        let currency = new Currency(id, 0, symbol, false, rate, true, this.first.symbol);
        this.currenciesArray.pop(); // remove null
        this.currenciesArray.push(currency, null);
        this.currenciesMap.set(id, currency);
        return currency;
    }

    update(id: number, symbol: string, rate: number) {
        console.log("Updating currency " + symbol);
        let currency: Currency = this.currenciesMap.get(id);
        currency.rate = rate;
        currency.symbol = symbol;
        return currency;
    }

    remove(id: number) {
        console.log("Removing currency by id " + id);
        let toBeRemoved: Currency = this.currenciesMap.get(id);
        let index: number = this.currenciesArray.indexOf(toBeRemoved);
        if (index > -1) {
            this.currenciesArray.splice(index, 1);
        }
        this.currenciesMap.delete(id);
    }
}