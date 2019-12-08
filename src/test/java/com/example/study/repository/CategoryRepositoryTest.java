package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Category;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class CategoryRepositoryTest extends StudyApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create(){
        Category category = new Category();
        category.setType("COMPUTER");
        category.setTitle("컴퓨터");
        category.setCreatedAt(LocalDateTime.now());
        category.setCreatedBy("admin");

        Category savedCategory = categoryRepository.save(category);

        Assert.assertNotNull(savedCategory);
        Assert.assertNotNull(savedCategory.getId());
    }

    @Test
    public void read(){

        String type = "COMPUTER";
        Optional<Category> findCategory = categoryRepository.findByType(type);

        findCategory.ifPresent(category -> {
            System.out.println(category.getId());
            System.out.println(category.getTitle());
            System.out.println(category.getType());
            Assert.assertEquals(category.getType(),type);
        });

    }
}
