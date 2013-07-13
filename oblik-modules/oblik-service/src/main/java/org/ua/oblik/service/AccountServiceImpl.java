package org.ua.oblik.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.ua.oblik.domain.beans.AccountKind;
import org.ua.oblik.domain.dao.AccountDao;
import org.ua.oblik.domain.dao.CurrencyDao;
import org.ua.oblik.domain.model.Account;
import org.ua.oblik.domain.model.Currency;
import org.ua.oblik.service.beans.AccountVO;
import org.ua.oblik.service.beans.AccountVOType;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 * @author Anton Bakalets
 */
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private CurrencyDao currencyDao;
    
    @Autowired
    private CurrencyService currencyService;

    @Override
    public AccountVO getAccount(Integer accountId) {
        return convert(accountDao.select(accountId));
    }

    @Override
    @Transactional
    public void save(AccountVO avo) {
        if (avo.getAccountId() == null) {
            insert(avo);
        } else {
            update(avo);
        }
    }
    
    @Override
    public BigDecimal totalAssets() {
		BigDecimal result = new BigDecimal(0);
    	List<AccountVO> list = getAssetsAccounts();
    	for (AccountVO avo : list) {
    		Integer currencyId = avo.getCurrencyId();
			CurrencyVO cvo = currencyService.getCurrency(currencyId);
			BigDecimal toAdd = avo.getAmmount().multiply(cvo.getRate());
			result = result.add(toAdd);
		}
    	return result;
    }
    
    
    private void insert(AccountVO avo) {
        LOGGER.debug("Saving new acoount, name: " + avo.getName());
        final Currency currency = currencyDao.select(avo.getCurrencyId());
        final Account account = new Account();
        account.setCurrency(currency);
        account.setKind(convertType(avo.getType())); // TODO
        account.setShortName(avo.getName());
        account.setTotal(avo.getAmmount());
        accountDao.insert(account);
        avo.setAccountId(account.getAccoId());
    }

    private void update(AccountVO avo) {
        LOGGER.debug("Updating acoount, name: " + avo.getName());
        final Currency currency = currencyDao.select(avo.getCurrencyId());
        final Account account = accountDao.select(avo.getAccountId());
        account.setCurrency(currency);
        account.setKind(convertType(avo.getType())); // TODO
        account.setShortName(avo.getName());
        account.setTotal(avo.getAmmount());
        accountDao.update(account);
    }

    @Override
    public List<AccountVO> getExpenseAccounts() {
        return convert(accountDao.selectByKind(AccountKind.EXPENSE));
    }

    @Override
    public List<AccountVO> getIncomeAccounts() {
        return convert(accountDao.selectByKind(AccountKind.INCOME));
    }

    @Override
    public List<AccountVO> getAssetsAccounts() {
        return convert(accountDao.selectByKind(AccountKind.ASSETS));
    }
    
    @Override
    public Map<CurrencyVO, BigDecimal> totalAssetsByCurrency(){
    	List<AccountVO> listAcc = getAssetsAccounts();
    	List<CurrencyVO> listCur = currencyService.getCurrencies();
    	HashMap<CurrencyVO, BigDecimal> result = new HashMap<CurrencyVO, BigDecimal>();
    	
    	for(CurrencyVO cvo:listCur){
    		BigDecimal toRes = new BigDecimal(0);
    		for(AccountVO avo : listAcc) {
    			if (avo.getCurrencyId() == cvo.getCurrencyId()) {
    				toRes = toRes.add(avo.getAmmount());
    			}
    		}
    		result.put(cvo, toRes);
    	}
    	return result;
    }
    
	@Override
	public boolean isNameExists(String name) {
		return accountDao.isNameExists(name);
	}

    
    
    private static AccountVO convert(Account model) {
        AccountVO result = new AccountVO();
        result.setAccountId(model.getId());
        result.setCurrencyId(model.getCurrency().getId());
        result.setCurrencySymbol(model.getCurrency().getSymbol());
        result.setName(model.getShortName());
        result.setAmmount(model.getTotal());
        result.setType(convertType(model.getKind()));
        return result;
    }

    private static List<AccountVO> convert(List<Account> modelList) {
        List<AccountVO> result = new ArrayList<AccountVO>(modelList.size());
        for (Account model : modelList) {
            result.add(convert(model));
        }
        return result;
    }

    private static AccountVOType convertType(AccountKind kind) {
        switch (kind) {
            case ASSETS:
                return AccountVOType.ASSETS;
            case EXPENSE:
                return AccountVOType.EXPENSE;
            case INCOME:
                return AccountVOType.INCOME;
        }
        throw new IllegalArgumentException("Unnexisting account type.");
    }

    private static AccountKind convertType(AccountVOType type) {
        switch (type) {
            case ASSETS:
                return AccountKind.ASSETS;
            case EXPENSE:
                return AccountKind.EXPENSE;
            case INCOME:
                return AccountKind.INCOME;
        }
        throw new IllegalArgumentException("Unnexisting account type.");
    }




}
