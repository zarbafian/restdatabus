package com.restdatabus.security;

import com.restdatabus.model.service.AccountService;
import com.restdatabus.security.model.Account;
import com.restdatabus.security.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AccountUserDetailsService implements UserDetailsService {


    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountService.findByUsername(username);

        if(account == null) {
            // Not found
            throw new UsernameNotFoundException("Invalid username");
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role: account.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
        }

        User userDetails = new User(account.getUsername(),
                account.getPassword(), account.isEnabled(),
                !account.isExpired(), !account.isCredentialsExpired(),
                !account.isLocked(), grantedAuthorities);

        return userDetails;
    }
}
