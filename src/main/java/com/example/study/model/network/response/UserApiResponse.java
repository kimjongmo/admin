package com.example.study.model.network.response;

import com.example.study.model.enumClass.UserStatus;
import com.example.study.model.network.response.orderGroup.OrderGroupApiResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiResponse {

    private Long id;

    private String account;

    private String password;

    private UserStatus status;

    private String email;

    private String phoneNumber;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<OrderGroupApiResponse> orderGroupApiResponseList;
}
