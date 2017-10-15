package com.restdatabus.model.service;

import com.restdatabus.dao.AccountDao;
import com.restdatabus.security.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceBean implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceBean.class);

    @Autowired
    private AccountDao accountDao;

    public Account findByUsername(String username) {

        LOG.debug("findByUsername: {}", username);

        return accountDao.findByUsername(username);
    }
}
