package com.restdatabus.model.service;

import com.restdatabus.security.model.Account;

public interface AccountService {

    Account findByUsername(String username);
}
