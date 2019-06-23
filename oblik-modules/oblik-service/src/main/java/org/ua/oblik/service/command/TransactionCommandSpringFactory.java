package org.ua.oblik.service.command;

import org.ua.oblik.service.beans.TransactionVO;

public interface TransactionCommandSpringFactory {

    InsertTxCommand.InsertIncomeCommand createInsertIncomeCommand(TransactionVO tvo);

    InsertTxCommand.InsertExpenseCommand createInsertExpenseCommand(TransactionVO tvo);

    InsertTxCommand.InsertTransferCommand createInsertTransferCommand(TransactionVO tvo);

    UpdateTxCommand.UpdateIncomeCommand createUpdateIncomeCommand(TransactionVO tvo);

    UpdateTxCommand.UpdateExpenseCommand createUpdateExpenseCommand(TransactionVO tvo);

    UpdateTxCommand.UpdateTransferCommand createUpdateTransferCommand(TransactionVO tvo);

    DeleteTxCommand createDeleteCommand(TransactionVO tvo);
}
