package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.AdminUser;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class AdminUserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    public void create(){
        AdminUser adminUser = new AdminUser();
        adminUser.setAccount("AdminUser02");
        adminUser.setPassword("AdminUser02");
        adminUser.setStatus("REGISTERED");
        adminUser.setRole("PARTNER");
        adminUser.setRegisteredAt(LocalDateTime.now());


        AdminUser newAdminUser = adminUserRepository.save(adminUser);

        Assert.assertNotNull(newAdminUser);
    }

    @Test
    public void update(){
        AdminUser findAdminUser = adminUserRepository.findById(3L).get();
        findAdminUser.setAccount("AdminUser002");

        adminUserRepository.save(findAdminUser);
    }
}
