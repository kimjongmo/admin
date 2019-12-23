package com.example.study.service.user;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import com.example.study.model.enumClass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.Pagination;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import com.example.study.model.network.response.item.ItemApiResponse;
import com.example.study.model.network.response.orderGroup.OrderGroupApiResponse;
import com.example.study.service.BaseService;
import com.example.study.service.item.ItemApiLogicService;
import com.example.study.service.orderGroup.OrderGroupApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;
    @Autowired
    private ItemApiLogicService itemApiLogicService;

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        UserApiRequest userApiRequest = request.getData();

        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User savedUser = baseRepository.save(user);

        return Header.OK(response(savedUser));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {

        Optional<User> optionalUser = baseRepository.findById(id);

        return optionalUser
                .map(user -> Header.OK(response(user)))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest = request.getData();

        Optional<User> optional = baseRepository.findById(userApiRequest.getId());

        return optional.map(user -> {
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setStatus(userApiRequest.getStatus())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
            ;
            if (userApiRequest.getStatus() == UserStatus.UNREGISTERED && user.getUnregisteredAt() == null) {
                user.setUnregisteredAt(LocalDateTime.now());
            } else if (userApiRequest.getStatus() == UserStatus.REGISTERED && user.getUnregisteredAt() != null) {
                user.setUnregisteredAt(null);
            }
            return user;
        })
                .map(user -> baseRepository.save(user))
                .map(user -> Header.OK(response(user)))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<User> optional = baseRepository.findById(id);
        return optional.map(user -> {
            baseRepository.delete(user);
            return Header.OK();
        })
                .orElseGet(() -> Header.ERROR("데이터 없음"));

    }

    private UserApiResponse response(User user) {
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        return userApiResponse;
    }

    public Header<List<UserApiResponse>> search(Pageable pageable) {
        Page<User> pages = baseRepository.findAll(pageable);
        List<UserApiResponse> users = pages.stream().map(this::response).collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(pages.getNumber())
                .currentElements(pages.getNumberOfElements())
                .build();

        return Header.OK(users, pagination);
    }

    public Header<UserOrderInfoApiResponse> orderInfo(Long id) {
        Optional<User> optionalUser = baseRepository.findById(id);

        if (!optionalUser.isPresent())
            return Header.ERROR("데이터 없음");

        User user = optionalUser.get();
        UserApiResponse userApiResponse = response(user);

        List<OrderGroup> orderGroupList = user.getOrderGroupList();
        List<OrderGroupApiResponse> orderGroupApiResponseList =
                orderGroupList.stream().map(orderGroup -> {
                    OrderGroupApiResponse orderGroupApiResponse = orderGroupApiLogicService.response(orderGroup);
                    List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
                            .map(detail -> detail.getItem())
                            .map(item -> itemApiLogicService.response(item))
                            .collect(Collectors.toList());
                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
                    return orderGroupApiResponse;
                }).collect(Collectors.toList());

        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);

        UserOrderInfoApiResponse userOrderInfoApiResponse
                = UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build();

        return Header.OK(userOrderInfoApiResponse);
    }
}
