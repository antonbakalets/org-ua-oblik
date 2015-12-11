package org.ua.oblik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.domain.dao.UserLoginDao;
import org.ua.oblik.domain.model.UserLogin;

public class ReportServiceImpl implements ReportService {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserLoginDao userLoginDao;

    @Autowired
    private MessageService messageService;

    @Override
    public void sendWeekReport() {
        List<? extends UserLogin> userLogins = userLoginDao.selectAll();
        for (UserLogin userLogin : userLogins) {
            String email = userLogin.getEmail();
            String from = "anton.bakalets@gmail.com";
            String subject = "Money manager";
            String msg = "Hello there from money manager.";
            messageService.sendMessage(from, email, subject, msg);
        }
    }
}
