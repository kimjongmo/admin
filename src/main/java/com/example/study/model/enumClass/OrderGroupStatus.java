package com.example.study.model.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderGroupStatus implements BaseStatus{

    COMPLETE(0,"배송 완료","배송 완료된 상태"),
    DELIVERY(1,"배송중","배송중인 상태"),
    WAITING(2,"대기중","준비중인 상태")
    ;

    private Integer id;
    private String title;
    private String description;
}
