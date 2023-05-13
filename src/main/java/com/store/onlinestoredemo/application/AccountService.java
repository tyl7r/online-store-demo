package com.store.onlinestoredemo.application;

import com.store.onlinestoredemo.api.account.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {


    public AccountResponse retrieveUserDetails() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return null;
    }

    public AccountResponse updateUserDetails() {
        return null;
    }
}
