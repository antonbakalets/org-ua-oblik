package org.ua.oblik.service.beans;

/**
 *
 * @author Anton Bakalets
 */
public class AccountCriteria {
    
    private AccountVOType type;
    
    private String currencySymbol;

    public AccountVOType getType() {
        return type;
    }

    public void setType(AccountVOType type) {
        this.type = type;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
    
    public static AccountCriteria EMPTY_CRITERIA = new Builder().build();
    
    public static AccountCriteria ASSETS_CRITERIA = new Builder().setType(AccountVOType.ASSETS).build();
    
    public static AccountCriteria EXPENSE_CRITERIA = new Builder().setType(AccountVOType.EXPENSE).build();
    
    public static AccountCriteria INCOME_CRITERIA = new Builder().setType(AccountVOType.INCOME).build();
    
    public static class Builder {
        
        private AccountVOType type;
    
        private String currencySymbol;

        public Builder setType(AccountVOType type) {
            this.type = type;
            return this;
        }

        public Builder setCurrencySymbol(String currencySymbol) {
            this.currencySymbol = currencySymbol;
            return this;
        }

        public AccountCriteria build() {
            AccountCriteria criteria = new AccountCriteria();
            criteria.setType(this.type);
            criteria.setCurrencySymbol(this.currencySymbol);
            return criteria;
        }
    }
}
