package org.ua.oblik.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.domain.dao.AccountRepository;
import org.ua.oblik.domain.dao.TxactionRepository;
import org.ua.oblik.service.beans.TransactionVO;

abstract class AbstractTxCommand implements TransactionCommand {

    protected final TransactionVO tvo;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected TxactionRepository txactionRepository;

    public AbstractTxCommand(TransactionVO tvo) {
        this.tvo = tvo;
    }
}
