package com.restdatabus.web.api;

import static com.restdatabus.web.api.Constants.PRINCIPAL;

import com.restdatabus.model.data.dvo.UserData;
import com.restdatabus.model.service.AccountService;
import com.restdatabus.security.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(
            value = PRINCIPAL,
            method = RequestMethod.GET
    )
    public ResponseEntity<UserData> principal(Principal principal) {

        if(principal == null) {
            LOGGER.error("no principal");
            throw new AccessDeniedException("no principal");
        }

        LOGGER.debug("principal is: {}", principal.getName());

        Account account = accountService.findByUsername(principal.getName());

        UserData userData = new UserData();
        userData.setName(principal.getName());
        account.getRoles().forEach(
                role -> userData.getRoles().add(role.getLabel())
        );

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }
}
