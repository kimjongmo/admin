package com.example.study.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType implements BaseStatus{

    CARD(0,"카드","카드로 결제"),
    CASH(1,"현금","현금 결제"),
    PHONE(2,"핸드폰","핸드폰 결제"),
    BANK_TRANSFER(3,"계좌 이체","계좌로 이체"),
    CHECK_CARD(4,"체크 카드","체크 카드로 결제")
    ;

    private Integer id;
    private String title;
    private String description;
}
