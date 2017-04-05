package org.ua.oblik.service;

import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.service.beans.AccountVOType;

import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Anton Bakalets
 */
public class AccountTypeConverter {

    private static final Map<AccountVOType, AccountKind> TYPE_TO_KIND = createTypeToKindMap();
    
    private static final Map<AccountKind, AccountVOType> KIND_TO_TYPE = createKindToTypeMap();

    private AccountTypeConverter() {
    }

    private static Map<AccountVOType, AccountKind> createTypeToKindMap() {
        Map<AccountVOType, AccountKind> result = new EnumMap<>(AccountVOType.class);
        result.put(AccountVOType.INCOME, AccountKind.INCOME);
        result.put(AccountVOType.EXPENSE, AccountKind.EXPENSE);
        result.put(AccountVOType.ASSETS, AccountKind.ASSETS);
        return result;
    }

    private static Map<AccountKind, AccountVOType> createKindToTypeMap() {
        Map<AccountKind, AccountVOType> result = new EnumMap<>(AccountKind.class);
        result.put(AccountKind.INCOME, AccountVOType.INCOME);
        result.put(AccountKind.EXPENSE, AccountVOType.EXPENSE);
        result.put(AccountKind.ASSETS, AccountVOType.ASSETS);
        return result;
    }
    
    public static AccountVOType convert(AccountKind kind) {
        return KIND_TO_TYPE.get(kind);
    }
    
    public static AccountKind convert(AccountVOType type) {
        return TYPE_TO_KIND.get(type);
    }
}
