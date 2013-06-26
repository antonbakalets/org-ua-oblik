package org.ua.oblik.service;

import org.ua.oblik.domain.model.Account;
import org.ua.oblik.service.beans.AccountVO;

/**
 *
 * @author Anton Bakalets
 */
class AccountServiceHelper {

    public static AccountVO convert(Account account) {
        AccountVO bean = new AccountVO();
        return bean;
    }
}
