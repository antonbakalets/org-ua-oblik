package org.ua.oblik.service.command;

public interface TransactionCommandSpringFactory {

    InsertIncomeCommand createInsertIncomeCommand();

    InsertExpenseCommand createInsertExpenseCommand();

    InsertTransferCommand createInsertTransferCommand();

    UpdateIncomeCommand createUpdateIncomeCommand();

    UpdateExpenseCommand createUpdateExpenseCommand();

    UpdateTransferCommand createUpdateTransferCommand();

    DeleteTxCommand createDeleteCommand();
}
