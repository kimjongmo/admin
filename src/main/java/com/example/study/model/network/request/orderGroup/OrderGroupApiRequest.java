package com.example.study.model.network.request.orderGroup;

import com.example.study.model.enumClass.OrderGroupStatus;
import com.example.study.model.enumClass.OrderStatus;
import com.example.study.model.enumClass.OrderType;
import com.example.study.model.enumClass.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderGroupApiRequest {
    private Long id;
    private OrderStatus status;
    private OrderType orderType;
    private String revAddress;
    private String revName;
    private PaymentType paymentType;
    private BigDecimal totalPrice;
    private Integer totalQuantity;
    private LocalDateTime orderAt;
    private LocalDateTime arrivalDate;
    private Long userId;
}
