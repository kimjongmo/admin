package com.example.study.model.network.response.defaultApi;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DefaultApiResponse {
    private Long itemCnt;
    private Long userCnt;
    private Long partnerCnt;
    private Long orderGroupCnt;
}
