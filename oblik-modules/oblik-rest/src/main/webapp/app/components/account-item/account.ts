import {Component, Input} from "@angular/core";
import {Account} from "../../services/account-service";

@Component({
    selector: 'oblik-account-item',
    templateUrl: 'app/components/account-item/account.html'
})
export default class AccountItemComponent {
    @Input() account: Account;
}