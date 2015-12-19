package org.ua.oblik.service.command;

import java.util.HashMap;
import java.util.Map;

import org.ua.oblik.service.beans.TransactionType;
import org.ua.oblik.service.beans.TransactionVO;

public class TransactionCommandFactory {

    private TransactionCommandSpringFactory springFactory;

    private final Map<TransactionType, Supplier<TransactionCommand>> insertCommands = new HashMap<>(3);

    private final Map<TransactionType, Supplier<TransactionCommand>> updateCommands = new HashMap<>(3);

    public void init() {
        insertCommands.put(TransactionType.INCOME, new Supplier<TransactionCommand>() {
            @Override
            public TransactionCommand get() {
                return springFactory.createInsertIncomeCommand();
            }
        });
        insertCommands.put(TransactionType.EXPENSE, new Supplier<TransactionCommand>() {
            @Override
            public TransactionCommand get() {
                return springFactory.createInsertExpenseCommand();
            }
        });
        insertCommands.put(TransactionType.TRANSFER, new Supplier<TransactionCommand>() {
            @Override
            public TransactionCommand get() {
                return springFactory.createInsertTransferCommand();
            }
        });

        updateCommands.put(TransactionType.INCOME, new Supplier<TransactionCommand>() {
            @Override
            public TransactionCommand get() {
                return springFactory.createUpdateIncomeCommand();
            }
        });
        updateCommands.put(TransactionType.EXPENSE, new Supplier<TransactionCommand>() {
            @Override
            public TransactionCommand get() {
                return springFactory.createUpdateExpenseCommand();
            }
        });
        updateCommands.put(TransactionType.TRANSFER, new Supplier<TransactionCommand>() {
            @Override
            public TransactionCommand get() {
                return springFactory.createUpdateTransferCommand();
            }
        });
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

    private abstract class Supplier<T> {
        public abstract T get();
    }
}


