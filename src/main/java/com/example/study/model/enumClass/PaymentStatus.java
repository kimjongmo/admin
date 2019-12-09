package com.example.study.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus implements BaseStatus{

    CARD(0,"카드","카드로 결제"),
    CASH(1,"현금","현금 결제"),
    PHONE(2,"핸드폰","핸드폰 결제")
    ;

    private Integer id;
    private String title;
    private String description;
}
