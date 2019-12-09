package com.example.study.model.network.request.adminUser;

import com.example.study.model.enumClass.AdminUserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserApiRequest {
    private Long id;
    private String account;
    private String password;
    private AdminUserStatus status;
    private String role;
}
