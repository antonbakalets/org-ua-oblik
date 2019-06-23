package org.ua.oblik.service.command;

import org.ua.oblik.service.beans.TransactionType;
import org.ua.oblik.service.beans.TransactionVO;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class TransactionCommandFactory {

    private TransactionCommandSpringFactory springFactory;

    private final Map<TransactionType, Function<TransactionVO, TransactionCommand>> insertCommands = new EnumMap<>(TransactionType.class);

    private final Map<TransactionType, Function<TransactionVO, TransactionCommand>> updateCommands = new EnumMap<>(TransactionType.class);

    public void init() {
        insertCommands.put(TransactionType.INCOME, tvo -> springFactory.createInsertIncomeCommand(tvo));
        insertCommands.put(TransactionType.EXPENSE, tvo -> springFactory.createInsertExpenseCommand(tvo));
        insertCommands.put(TransactionType.TRANSFER, tvo -> springFactory.createInsertTransferCommand(tvo));

        updateCommands.put(TransactionType.INCOME, tvo -> springFactory.createUpdateIncomeCommand(tvo));
        updateCommands.put(TransactionType.EXPENSE, tvo -> springFactory.createUpdateExpenseCommand(tvo));
        updateCommands.put(TransactionType.TRANSFER, tvo -> springFactory.createUpdateTransferCommand(tvo));
    }

    public TransactionCommand createSaveCommand(TransactionVO tvo) {
        return tvo.getTxId() == null
                ? insertCommands.get(tvo.getType()).apply(tvo)
                : updateCommands.get(tvo.getType()).apply(tvo);
    }

    public TransactionCommand createDeleteCommand(TransactionVO tvo) {
        return springFactory.createDeleteCommand(tvo);
    }

    public void setSpringFactory(TransactionCommandSpringFactory springFactory) {
        this.springFactory = springFactory;
    }
}


