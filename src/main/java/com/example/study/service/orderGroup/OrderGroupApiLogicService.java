package com.example.study.service.orderGroup;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.orderGroup.OrderGroupApiRequest;
import com.example.study.model.network.response.orderGroup.OrderGroupApiResponse;
import com.example.study.repository.UserRepository;
import com.example.study.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderGroupApiLogicService extends BaseService<OrderGroupApiRequest,OrderGroupApiResponse,OrderGroup> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest data = request.getData();

        OrderGroup orderGroup = OrderGroup.builder()
                .status(data.getStatus())
                .orderType(data.getOrderType())
                .revAddress(data.getRevAddress())
                .revName(data.getRevName())
                .paymentType(data.getPaymentType())
                .totalPrice(data.getTotalPrice())
                .totalQuantity(data.getTotalQuantity())
                .orderAt(LocalDateTime.now())
                .user(userRepository.getOne(data.getUserId()))
                .build();

        OrderGroup newOrderGroup = baseRepository.save(orderGroup);

        return response(newOrderGroup);
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(orderGroup -> response(orderGroup))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest data = request.getData();

        Optional<OrderGroup> optionalOrderGroup = baseRepository.findById(data.getId());

        return optionalOrderGroup.map(orderGroup -> {
            orderGroup.setStatus(data.getStatus())
                    .setOrderType(data.getOrderType())
                    .setPaymentType(data.getPaymentType())
                    .setRevAddress(data.getRevAddress())
                    .setRevName(data.getRevName())
                    .setTotalPrice(data.getTotalPrice())
                    .setTotalQuantity(data.getTotalQuantity())
                    .setArrivalDate(data.getArrivalDate())
                    .setOrderAt(data.getOrderAt())
                    .setUser(userRepository.getOne(data.getId()))
                    ;
            return orderGroup;
        }).map(orderGroup->baseRepository.save(orderGroup))
          .map(orderGroup->response(orderGroup))
          .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<OrderGroup> optionalOrderGroup = baseRepository.findById(id);
        return optionalOrderGroup.map(orderGroup -> {
            baseRepository.delete(orderGroup);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private Header<OrderGroupApiResponse> response(OrderGroup orderGroup) {
        OrderGroupApiResponse body = OrderGroupApiResponse.builder()
                .id(orderGroup.getId())
                .status(orderGroup.getStatus())
                .orderType(orderGroup.getOrderType())
                .revAddress(orderGroup.getRevAddress())
                .revName(orderGroup.getRevName())
                .paymentType(orderGroup.getPaymentType())
                .totalPrice(orderGroup.getTotalPrice())
                .totalQuantity(orderGroup.getTotalQuantity())
                .orderAt(orderGroup.getOrderAt())
                .arrivalDate(orderGroup.getArrivalDate())
                .userId(orderGroup.getUser().getId())
                .build();

        return Header.OK(body);
    }
}
