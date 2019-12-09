package com.example.study.service.partner;

import com.example.study.model.entity.Partner;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.partner.PartnerApiRequest;
import com.example.study.model.network.response.partner.PartnerApiResponse;
import com.example.study.repository.CategoryRepository;
import com.example.study.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PartnerApiLogicService extends BaseService<PartnerApiRequest, PartnerApiResponse, Partner> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Header<PartnerApiResponse> create(Header<PartnerApiRequest> request) {
        PartnerApiRequest data = request.getData();

        Partner partner = Partner.builder()
                .status(data.getStatus())
                .name(data.getName())
                .address(data.getAddress())
                .businessNumber(data.getBusinessNumber())
                .callCenter(data.getCallCenter())
                .category(categoryRepository.getOne(data.getCategoryId()))
                .ceoName(data.getCeoName())
                .partnerNumber(data.getPartnerNumber())
                .registeredAt(LocalDateTime.now())
                .build();

        Partner newPartner = baseRepository.save(partner);

        return response(newPartner);
    }

    @Override
    public Header<PartnerApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(partner -> response(partner))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<PartnerApiResponse> update(Header<PartnerApiRequest> request) {
        PartnerApiRequest data = request.getData();
        Optional<Partner> optional = baseRepository.findById(data.getId());
        return optional.map(partner -> {
          partner.setName(data.getName())
                  .setCeoName(data.getCeoName())
                  .setRegisteredAt(data.getRegisteredAt())
                  .setBusinessNumber(data.getBusinessNumber())
                  .setPartnerNumber(data.getPartnerNumber())
                  .setCallCenter(data.getCallCenter())
                  .setAddress(data.getAddress())
                  .setCategory(categoryRepository.getOne(data.getId()))
                  .setStatus(data.getStatus());
          return partner;
        }).map(partner->baseRepository.save(partner))
          .map(partner->response(partner))
          .orElseGet(()->Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(partner -> {
                    baseRepository.delete(partner);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private Header<PartnerApiResponse> response(Partner partner){
        PartnerApiResponse body = PartnerApiResponse.builder()
                .id(partner.getId())
                .status(partner.getStatus())
                .name(partner.getName())
                .address(partner.getAddress())
                .businessNumber(partner.getBusinessNumber())
                .callCenter(partner.getCallCenter())
                .categoryId(partner.getCategory().getId())
                .ceoName(partner.getCeoName())
                .partnerNumber(partner.getPartnerNumber())
                .registeredAt(partner.getRegisteredAt())
                .unregisteredAt(partner.getUnregisteredAt())
                .build();
        return Header.OK(body);
    }
}
