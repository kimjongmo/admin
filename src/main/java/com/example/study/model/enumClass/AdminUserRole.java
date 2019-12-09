package com.example.study.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminUserRole {
    ADMIN(),
    MANAGER(),
    PARTNER();
}
