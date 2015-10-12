package org.ua.oblik.service.command;

import org.ua.oblik.service.beans.TransactionVO;

public interface TransactionCommand {

    void execute();

    void setTransactionVO(TransactionVO tvo);
}
