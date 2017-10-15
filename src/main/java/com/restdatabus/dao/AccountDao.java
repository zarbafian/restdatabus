package com.restdatabus.dao;

import com.restdatabus.security.model.Account;
import com.restdatabus.security.model.Role;

import java.util.List;

public interface AccountDao {

    Account findByUsername(String username);

    List<Role> getRoles(Account account);
}
