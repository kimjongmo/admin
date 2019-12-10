package com.example.study.model.network.response.adminUser;

import com.example.study.model.enumClass.AdminUserRole;
import com.example.study.model.enumClass.AdminUserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserApiResponse {
    private Long id;
    private String account;
    private String password;
    private AdminUserStatus status;
    private AdminUserRole role;
}
