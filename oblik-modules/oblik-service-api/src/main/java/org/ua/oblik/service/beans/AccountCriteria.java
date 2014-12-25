package org.ua.oblik.service.beans;

/**
 *
 * @author Anton Bakalets
 */
public class AccountCriteria {
    
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

    public static AccountCriteria EMPTY_CRITERIA = new Builder().build();
    
    public static AccountCriteria ASSETS_CRITERIA = new Builder().setType(AccountVOType.ASSETS).build();
    
    public static AccountCriteria EXPENSE_CRITERIA = new Builder().setType(AccountVOType.EXPENSE).build();
    
    public static AccountCriteria INCOME_CRITERIA = new Builder().setType(AccountVOType.INCOME).build();
    
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
