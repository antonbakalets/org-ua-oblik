package org.ua.oblik.service.command;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import org.ua.oblik.service.beans.TransactionType;
import org.ua.oblik.service.beans.TransactionVO;

public class TransactionCommandFactory {

    private TransactionCommandSpringFactory springFactory;

    private final Map<TransactionType, Supplier<TransactionCommand>> insertCommands = new EnumMap<>(TransactionType.class);

    private final Map<TransactionType, Supplier<TransactionCommand>> updateCommands = new EnumMap<>(TransactionType.class);

    public void init() {
        insertCommands.put(TransactionType.INCOME, springFactory::createInsertIncomeCommand);
        insertCommands.put(TransactionType.EXPENSE, springFactory::createInsertExpenseCommand);
        insertCommands.put(TransactionType.TRANSFER, springFactory::createInsertTransferCommand);

        updateCommands.put(TransactionType.INCOME, springFactory::createUpdateIncomeCommand);
        updateCommands.put(TransactionType.EXPENSE, springFactory::createUpdateExpenseCommand);
        updateCommands.put(TransactionType.TRANSFER, springFactory::createUpdateTransferCommand);
    }

    public TransactionCommand createSaveCommand(TransactionVO tvo) {
        TransactionCommand command =
                tvo.getTxId() == null
                ? insertCommands.get(tvo.getType()).get()
                : updateCommands.get(tvo.getType()).get();
        command.setTransactionVO(tvo);
        return command;
    }

    public TransactionCommand createDeleteCommand(TransactionVO tvo) {
        TransactionCommand deleteCommand = springFactory.createDeleteCommand();
        deleteCommand.setTransactionVO(tvo);
        return deleteCommand;
    }

    public void setSpringFactory(TransactionCommandSpringFactory springFactory) {
        this.springFactory = springFactory;
    }
}


