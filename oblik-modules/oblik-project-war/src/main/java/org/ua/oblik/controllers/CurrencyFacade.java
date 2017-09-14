package org.ua.oblik.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.controllers.beans.CurrencyEditBean;
import org.ua.oblik.controllers.beans.CurrencyListBean;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.CurrencyService;
import org.ua.oblik.service.NotFoundException;
import org.ua.oblik.service.beans.CurrencyVO;

/**
 *
 */
class CurrencyFacade extends AbstractHelper {

    @Autowired
    private CurrencyService currencyService;

    public List<CurrencyListBean> getCurrencies(Locale locale) {
        List<CurrencyVO> list = currencyService.getCurrencies();
        return convert(list, locale);
    }

    public String getDefaultCurrencySymbol() {
        return currencyService.getDefaultCurrency().getSymbol();
    }

    public CurrencyEditBean createCurrencyBean(final Optional<Integer> currencyId) {
        CurrencyVO result;
        if (currencyId.isPresent()) {
            result = currencyService.getCurrency(currencyId.get());
        } else {
            result = currencyService.createCurrency();
        }
        return convert(result);
    }

    public void save(CurrencyEditBean currencyEditBean) {
        CurrencyVO cvo = convert(currencyEditBean);
        currencyService.save(cvo);
    }

    public void remove(Integer currencyId) throws NotFoundException, BusinessConstraintException {
        currencyService.remove(currencyId);
    }

    private List<CurrencyListBean> convert(List<CurrencyVO> list, Locale locale) {
        List<CurrencyListBean> result = new ArrayList<>();
        for (CurrencyVO vo : list) {
            result.add(convert(vo, locale));
        }
        return result;
    }

    private CurrencyListBean convert(CurrencyVO vo, Locale locale) {
        CurrencyListBean result = new CurrencyListBean();
        result.setCurrencyId(vo.getCurrencyId());
        result.setTotal(formatDecimal(vo.getTotal(), locale));
        result.setRate(formatDecimal(vo.getRate(), locale));
        result.setSymbol(vo.getSymbol());
        result.setDefaultRate(vo.getDefaultRate());
        return result;
    }

    private CurrencyVO convert(CurrencyEditBean currencyEditBean) {
        CurrencyVO result = new CurrencyVO();
        result.setCurrencyId(currencyEditBean.getCurrencyId());
        result.setRate(currencyEditBean.getRate());
        result.setSymbol(currencyEditBean.getSymbol());
        result.setDefaultRate(currencyEditBean.getDefaultRate());
        return result;
    }

    private CurrencyEditBean convert(CurrencyVO cvo) {
        CurrencyEditBean result = new CurrencyEditBean();
        result.setCurrencyId(cvo.getCurrencyId());
        result.setRate(cvo.getRate());
        result.setSymbol(cvo.getSymbol());
        result.setDefaultRate(cvo.getDefaultRate());
        result.setRemovable(cvo.isRemovable());
        return result;
    }
}
