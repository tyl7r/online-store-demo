package com.store.onlinestoredemo.api.account;

import com.store.onlinestoredemo.application.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    public final AccountService service;

    @GetMapping
    public ResponseEntity<AccountResponse> retrieveUserDetails() {
        return ResponseEntity.ok(service.retrieveUserDetails());
    }

    @PutMapping("updateInfo")
    public ResponseEntity<AccountResponse> updateUserDetails() {
        return ResponseEntity.ok(service.updateUserDetails());
    }




}
/*

USER:
/accounts/*
GET /accounts - Returns User Details
PUT /accounts/updateInfo (UserRequest) - Updates User details


BUSINESS:
/accounts/*
GET /accounts?accountId={id} - Get Account Info by Id
GET /accounts/getOrderHistory={id} - Get Order History by Id
----------------------------------------
Admin Account:
/accounts/*
DEL /accounts?accountId={id} - Delete Account by Id
PUT /accounts/updateInfo?accountId=123 (UserRequest) - Updates a users details
 */