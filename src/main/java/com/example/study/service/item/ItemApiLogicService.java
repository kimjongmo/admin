package com.example.study.service.item;

import com.example.study.model.entity.Item;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.item.ItemApiRequest;
import com.example.study.model.network.response.Pagination;
import com.example.study.model.network.response.item.ItemApiResponse;
import com.example.study.repository.PartnerRepository;
import com.example.study.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item>{

    @Autowired
    private PartnerRepository partnerRepository;
    
    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {

        ItemApiRequest data = request.getData();

        Item item = Item.builder()
                .status(data.getStatus())
                .name(data.getName())
                .title(data.getTitle())
                .content(data.getContent())
                .price(data.getPrice())
                .brandName(data.getBrandName())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(data.getPartnerId()))
                .build();

        Item saveItem = baseRepository.save(item);

        return Header.OK(response(saveItem));
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        Optional<Item> optionalItem = baseRepository.findById(id);
        return optionalItem.map(item -> Header.OK(response(item)))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest data = request.getData();

        Optional<Item> optionalItem = baseRepository.findById(data.getId());

        return optionalItem.map(item -> {
            item.setStatus(data.getStatus())
                    .setName(data.getName())
                    .setTitle(data.getTitle())
                    .setContent(data.getContent())
                    .setPrice(data.getPrice())
                    .setBrandName(data.getBrandName())
                    .setRegisteredAt(data.getRegisteredAt())
                    .setUnregisteredAt(data.getUnregisteredAt());

            return item;
        }).map(item -> baseRepository.save(item))
        .map(item -> Header.OK(response(item)))
        .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<Item> optionalItem = baseRepository.findById(id);
        return optionalItem.map(item -> {
            baseRepository.delete(item);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    public ItemApiResponse response(Item item) {
        return ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();
    }

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {
        Page<Item> pages = baseRepository.findAll(pageable);
        List<ItemApiResponse> users = pages.stream().map(this::response).collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(pages.getNumber())
                .currentElements(pages.getNumberOfElements())
                .build();

        return Header.OK(users,pagination);
    }
}
