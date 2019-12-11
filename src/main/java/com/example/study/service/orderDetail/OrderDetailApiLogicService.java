package com.example.study.service.orderDetail;

import com.example.study.model.entity.OrderDetail;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.orderDetail.OrderDetailApiRequest;
import com.example.study.model.network.response.Pagination;
import com.example.study.model.network.response.orderDetail.OrderDetailApiResponse;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderGroupRepository;
import com.example.study.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailApiLogicService extends BaseService<OrderDetailApiRequest, OrderDetailApiResponse, OrderDetail> {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Override
    public Header<OrderDetailApiResponse> create(Header<OrderDetailApiRequest> request) {

        OrderDetailApiRequest data = request.getData();

        OrderDetail orderDetail = OrderDetail.builder()
                .status(data.getStatus())
                .arrivalDate(data.getArrivalDate())
                .quantity(data.getQuantity())
                .totalPrice(data.getTotalPrice())
                .item(itemRepository.getOne(data.getItemId()))
                .orderGroup(orderGroupRepository.getOne(data.getOrderGroupId()))
                .build();

        OrderDetail save = baseRepository.save(orderDetail);
        return Header.OK(response(save));
    }

    @Override
    public Header<OrderDetailApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest data = request.getData();

        return baseRepository.findById(data.getId())
                .map(orderDetail -> {
                    return orderDetail.setStatus(data.getStatus())
                            .setArrivalDate(data.getArrivalDate())
                            .setQuantity(data.getQuantity())
                            .setTotalPrice(data.getTotalPrice())
                            .setItem(itemRepository.getOne(data.getItemId()))
                            .setOrderGroup(orderGroupRepository.getOne(data.getOrderGroupId()));
                })
                .map(o->baseRepository.save(o))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(orderDetail -> {
                    baseRepository.delete(orderDetail);
                    return Header.OK();
                }).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private OrderDetailApiResponse response(OrderDetail orderDetail){
        OrderDetailApiResponse body = OrderDetailApiResponse.builder()
                .id(orderDetail.getId())
                .status(orderDetail.getStatus())
                .arrivalDate(orderDetail.getArrivalDate())
                .quantity(orderDetail.getQuantity())
                .totalPrice(orderDetail.getTotalPrice())
                .itemId(orderDetail.getItem().getId())
                .orderGroupId(orderDetail.getOrderGroup().getId())
                .build();
        return body;
    }

    @Override
    public Header<List<OrderDetailApiResponse>> search(Pageable pageable) {
        Page<OrderDetail> pages = baseRepository.findAll(pageable);
        List<OrderDetailApiResponse> users = pages.stream().map(this::response).collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(pages.getNumber())
                .currentElements(pages.getNumberOfElements())
                .build();

        return Header.OK(users,pagination);
    }
}
