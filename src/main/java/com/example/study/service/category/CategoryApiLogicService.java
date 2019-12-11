package com.example.study.service.category;

import com.example.study.model.entity.Category;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.category.CategoryApiRequest;
import com.example.study.model.network.response.Pagination;
import com.example.study.model.network.response.category.CategoryApiResponse;
import com.example.study.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryApiLogicService extends BaseService<CategoryApiRequest, CategoryApiResponse, Category> {

    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {
        CategoryApiRequest data = request.getData();

        Category category = Category.builder()
                .title(data.getTitle())
                .type(data.getType())
                .build();

        Category saveCategory = baseRepository.save(category);

        return Header.OK(response(saveCategory));
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(category -> Header.OK(response(category)))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {
        CategoryApiRequest data = request.getData();

        return baseRepository.findById(data.getId())
        .map(category -> {
            return category.setTitle(data.getTitle())
                    .setType(data.getType());
        }).map(c -> baseRepository.save(c))
          .map(this::response)
          .map(Header::OK)
          .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(category -> {
                    baseRepository.delete(category);
                    return Header.OK();
                }).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private CategoryApiResponse response(Category category){
        CategoryApiResponse body = CategoryApiResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .type(category.getType())
                .build();
        return body;
    }

    @Override
    public Header<List<CategoryApiResponse>> search(Pageable pageable) {
        Page<Category> pages = baseRepository.findAll(pageable);
        List<CategoryApiResponse> users = pages.stream().map(this::response).collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(pages.getNumber())
                .currentElements(pages.getNumberOfElements())
                .build();

        return Header.OK(users,pagination);
    }
}
