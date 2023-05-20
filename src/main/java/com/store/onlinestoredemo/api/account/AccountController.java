package com.store.onlinestoredemo.api.account;

import com.store.onlinestoredemo.application.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    public final AccountService service;

    @GetMapping
    public ResponseEntity<AccountResponse> retrieveUserDetails() {
        return ResponseEntity.ok(service.retrieveUserDetails());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AccountResponse> adminRetrieveUserDetails(@PathVariable Integer userId) {
        return ResponseEntity.ok(service.adminRetrieveUserDetails(userId));
    }

    @GetMapping("/getOrderHistory")
    public ResponseEntity<String> retrieveUserOrderHistory() {
        return ResponseEntity.ok(service.retrieveUserOrderHistory(null));
    }

    @GetMapping("/getUserOrderHistory/{userId}")
    public ResponseEntity<String> retrieveUserOrderHistory(@PathVariable Integer userId) {
        return ResponseEntity.ok(service.retrieveUserOrderHistory(userId));
    }

    @PutMapping("/updateInfo")
    public ResponseEntity<AccountResponse> updateUserDetails(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(service.updateUserDetails(request, false));
    }

    @PutMapping("/updateUserInfo")
    public ResponseEntity<AccountResponse> adminUpdateUserDetails(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(service.updateUserDetails(request, true));
    }

    @DeleteMapping("/{userId}")
    public void deleteUserDetails(@PathVariable int userId) {
        service.deleteUserDetails(userId);
    }

}