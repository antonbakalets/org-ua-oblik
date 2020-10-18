package org.ua.oblik.service.command;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.ua.oblik.service.beans.TransactionVO;

@Component
public interface TransactionCommandSpringFactory {

    @Lookup
    InsertTxCommand.InsertIncomeCommand createInsertIncomeCommand(TransactionVO tvo);

    @Lookup
    InsertTxCommand.InsertExpenseCommand createInsertExpenseCommand(TransactionVO tvo);

    @Lookup
    InsertTxCommand.InsertTransferCommand createInsertTransferCommand(TransactionVO tvo);

    @Lookup
    UpdateTxCommand.UpdateIncomeCommand createUpdateIncomeCommand(TransactionVO tvo);

    @Lookup
    UpdateTxCommand.UpdateExpenseCommand createUpdateExpenseCommand(TransactionVO tvo);

    @Lookup
    UpdateTxCommand.UpdateTransferCommand createUpdateTransferCommand(TransactionVO tvo);

    @Lookup
    DeleteTxCommand createDeleteCommand(TransactionVO tvo);
}
