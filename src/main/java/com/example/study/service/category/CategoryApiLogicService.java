package com.example.study.service.category;

import com.example.study.model.entity.Category;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.category.CategoryApiRequest;
import com.example.study.model.network.response.category.CategoryApiResponse;
import com.example.study.service.BaseService;
import org.springframework.stereotype.Service;

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

        return response(saveCategory);
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(category -> response(category))
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

    private Header<CategoryApiResponse> response(Category category){
        CategoryApiResponse body = CategoryApiResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .type(category.getType())
                .build();
        return Header.OK(body);
    }
}
