package com.example.study.service.defaultApi;

import com.example.study.model.network.Header;
import com.example.study.model.network.response.defaultApi.DefaultApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderGroupRepository;
import com.example.study.repository.PartnerRepository;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultApiLogicService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PartnerRepository partnerRepository;
    @Autowired
    private OrderGroupRepository orderGroupRepository;
    @Autowired
    private UserRepository userRepository;


    public Header<DefaultApiResponse> count(){
        DefaultApiResponse data = DefaultApiResponse.builder()
                .itemCnt(itemRepository.count())
                .partnerCnt(partnerRepository.count())
                .orderGroupCnt(orderGroupRepository.count())
                .userCnt(userRepository.count())
                .build();
        return Header.OK(data);

    }




}
