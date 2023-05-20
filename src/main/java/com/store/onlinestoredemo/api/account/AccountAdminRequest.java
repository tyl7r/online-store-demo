package com.store.onlinestoredemo.api.account;

import com.store.onlinestoredemo.core.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountAdminRequest {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

}
