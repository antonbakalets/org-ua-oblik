package org.ua.oblik.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.domain.dao.AccountDao;
import org.ua.oblik.domain.dao.TxactionDao;
import org.ua.oblik.domain.model.EntitiesFactory;
import org.ua.oblik.service.beans.TransactionVO;

abstract class AbstractTxCommand implements TransactionCommand {

    protected EntitiesFactory entitiesFactory;

    protected AccountDao accountDao;

    protected TxactionDao txactionDao;

    protected TransactionVO tvo;

    @Override
    public void setTransactionVO(TransactionVO tvo) {
        this.tvo = tvo;
    }

    @Autowired
    public void setEntitiesFactory(EntitiesFactory entitiesFactory) {
        this.entitiesFactory = entitiesFactory;
    }

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Autowired
    public void setTxactionDao(TxactionDao txactionDao) {
        this.txactionDao = txactionDao;
    }
}
