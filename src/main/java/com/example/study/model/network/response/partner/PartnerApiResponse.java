package com.example.study.model.network.response.partner;

import com.example.study.model.enumClass.PartnerStatus;
import com.example.study.model.network.response.item.ItemApiResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerApiResponse {

    private Long id;
    private PartnerStatus status;
    private String name;
    private String address;
    private String callCenter;
    private String partnerNumber;
    private String businessNumber;
    private String ceoName;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private Long categoryId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ItemApiResponse> itemList;

}
