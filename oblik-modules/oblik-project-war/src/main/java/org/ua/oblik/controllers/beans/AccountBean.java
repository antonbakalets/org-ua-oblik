package org.ua.oblik.controllers.beans;

import java.math.BigDecimal;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.ua.oblik.service.beans.AccountVOType;



/**
*
* @author Ihor Senkiv
*/
public class AccountBean {
	
	private Integer accountId;
	
	@NotNull
	private String name;
	
    @Enumerated(EnumType.STRING)
    private AccountVOType kind;
    
	@NotNull
    private Integer currencyId;
	
	private BigDecimal ammount;
    

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccountVOType getKind() {
		return kind;
	}

	public void setKind(AccountVOType kind) {
		this.kind = kind;
	}

    public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getAmmount() {
		return ammount;
	}

	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
	}


}
