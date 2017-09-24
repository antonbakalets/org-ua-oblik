package org.ua.oblik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ua.oblik.domain.dao.UserLoginDao;
import org.ua.oblik.domain.model.UserLogin;

public class ReportServiceImpl implements ReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    public static final String REPORT_FROM = "anton.bakalets@gmail.com";

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserLoginDao userLoginDao;

    @Autowired
    private MessageService messageService;

    @Override
    public void sendWeekReport() {
        LOGGER.info("Sending weekly repot.");

        for (UserLogin userLogin : userLoginDao.selectAll()) {
            String email = userLogin.getEmail();
            String from = REPORT_FROM;
            String subject = "Money manager";
            String msg = "Hello there from money manager.";
            messageService.sendMessage(from, email, subject, msg);
        }
    }
}
