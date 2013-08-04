package org.ua.oblik.controllers.beans;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.TransactionType;

public class TransactionBean {
	    private Integer Id;
	    
	    @Enumerated(EnumType.STRING)
	    private TransactionType type;
	    
	    @NotNull
	    private AccountVO firstAccount;
	    
	    @NotNull
	    private BigDecimal firstAmmount;
	    
	    private Date date;
	    
	    private String note;
	    
	    @NotNull
	    private AccountVO secondAccount;
	    
	    @NotNull
	    private BigDecimal secondAmmount;

		public Integer getId() {
			return Id;
		}

		public void setId(Integer id) {
			Id = id;
		}

		public TransactionType getType() {
			return type;
		}

		public void setType(TransactionType type) {
			this.type = type;
		}

		public AccountVO getFirstAccount() {
			return firstAccount;
		}

		public void setFirstAccount(AccountVO firstAccount) {
			this.firstAccount = firstAccount;
		}

		public BigDecimal getFirstAmmount() {
			return firstAmmount;
		}

		public void setFirstAmmount(BigDecimal firstAmmount) {
			this.firstAmmount = firstAmmount;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public AccountVO getSecondAccount() {
			return secondAccount;
		}

		public void setSecondAccount(AccountVO secondAccount) {
			this.secondAccount = secondAccount;
		}

		public BigDecimal getSecondAmmount() {
			return secondAmmount;
		}

		public void setSecondAmmount(BigDecimal secondAmmount) {
			this.secondAmmount = secondAmmount;
		}
	    
	    
	    

}
