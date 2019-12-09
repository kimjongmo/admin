package com.example.study.service.adminUser;


import com.example.study.model.entity.AdminUser;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.adminUser.AdminUserApiRequest;
import com.example.study.model.network.response.adminUser.AdminUserApiResponse;
import com.example.study.service.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminUserApiLogicService extends BaseService<AdminUserApiRequest, AdminUserApiResponse, AdminUser> {
    @Override
    public Header<AdminUserApiResponse> create(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest data = request.getData();

        AdminUser adminUser = AdminUser.builder()
                .account(data.getAccount())
                .password(data.getPassword())
                .status(data.getStatus())
                .role(data.getRole())
                .passwordUpdatedAt(LocalDateTime.now())
                .loginFailCount(0)
                .registeredAt(LocalDateTime.now())
                .build();

        AdminUser save = baseRepository.save(adminUser);

        return response(save);
    }

    @Override
    public Header<AdminUserApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<AdminUserApiResponse> update(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest data = request.getData();

        return baseRepository.findById(data.getId())
        .map(adminUser -> {
            return adminUser.setAccount(data.getAccount())
                    .setRole(data.getRole())
                    .setPassword(data.getPassword())
                    .setStatus(data.getStatus());
        }).map(u -> baseRepository.save(u))
          .map(this::response)
          .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(adminUser -> {
                    baseRepository.delete(adminUser);
                    return Header.OK();
                }).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private Header<AdminUserApiResponse> response(AdminUser adminUser){
        AdminUserApiResponse body = AdminUserApiResponse.builder()
                .id(adminUser.getId())
                .account(adminUser.getAccount())
                .password(adminUser.getPassword())
                .status(adminUser.getStatus())
                .role(adminUser.getRole())
                .build();
        return Header.OK(body);
    }
}
