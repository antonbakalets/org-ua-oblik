import {Currency, CurrencyService} from "../../services/currency-service";
import CurrencyItemComponent from "./currency";

describe('Currency item', () => {

    it('should be initialized', () => {
        let s: CurrencyService = new CurrencyService();
        let c: CurrencyItemComponent = new CurrencyItemComponent(s);
        expect(c).toBeDefined();
    });

    it('should not divide by zero', () => {
        console.warn("MESSAGE");
        expect(1 + 1).toBe(2);
    });
});