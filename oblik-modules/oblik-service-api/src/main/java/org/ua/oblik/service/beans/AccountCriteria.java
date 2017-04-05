package org.ua.oblik.service.beans;

/**
 *
 * @author Anton Bakalets
 */
public class AccountCriteria {

    public static final AccountCriteria EMPTY_CRITERIA = new Builder().build();

    public static final AccountCriteria ASSETS_CRITERIA = new Builder().setType(AccountVOType.ASSETS).build();

    public static final AccountCriteria EXPENSE_CRITERIA = new Builder().setType(AccountVOType.EXPENSE).build();

    public static final AccountCriteria INCOME_CRITERIA = new Builder().setType(AccountVOType.INCOME).build();

    private AccountVOType type;
    
    private Integer currencyId;

    private Integer excludeAccountId;

    public AccountVOType getType() {
        return type;
    }

    public void setType(AccountVOType type) {
        this.type = type;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getExcludeAccountId() {
        return excludeAccountId;
    }

    public void setExcludeAccountId(Integer excludeAccountId) {
        this.excludeAccountId = excludeAccountId;
    }

    public static class Builder {
        
        private AccountVOType type;
    
        private Integer currencyId;

        private Integer excludeAccountId;

        public Builder setType(AccountVOType type) {
            this.type = type;
            return this;
        }

        public Builder setCurrencyId(Integer currencyId) {
            this.currencyId = currencyId;
            return this;
        }

        public Builder excludeAccountId(Integer accountId) {
            this.excludeAccountId = accountId;
            return this;
        }

        public AccountCriteria build() {
            AccountCriteria criteria = new AccountCriteria();
            criteria.setType(this.type);
            criteria.setCurrencyId(this.currencyId);
            criteria.setExcludeAccountId(this.excludeAccountId);
            return criteria;
        }

    }
}
