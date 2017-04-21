import {Component, Input} from "@angular/core";

@Component({
    selector: 'oblik-accounts',
    templateUrl: 'app/components/accounts/accounts.html'
})
export default class AccountsComponent {
    @Input() kind: string;
    accounts: Array<Account>;

    public add() {
        console.log("add");
    }
}
