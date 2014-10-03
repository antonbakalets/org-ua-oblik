package org.ua.oblik.controllers.beans;

/**
 *
 * @author Anton Bakalets
 */
public class AccountOption {
    
    private Integer id;
    
    private String name;
    
    private Integer currency;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }
}
