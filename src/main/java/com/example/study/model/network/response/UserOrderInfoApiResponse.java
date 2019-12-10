package com.example.study.model.network.response;


import com.example.study.model.network.response.item.ItemApiResponse;
import com.example.study.model.network.response.orderGroup.OrderGroupApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderInfoApiResponse {
    private UserApiResponse userApiResponse;
}
