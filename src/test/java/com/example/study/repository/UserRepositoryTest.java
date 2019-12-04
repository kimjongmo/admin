package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void create(){
        User user = new User();
        user.setAccount("user02");
        user.setEmail("user02@gmail.com");
        user.setPhoneNumber("010-1111-2222");
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("admin");

        userRepository.save(user);

        System.out.println("id : "+user.getId());
    }

    @Test
    public void read(){
        Optional<User> user = userRepository.findById(1L);

        if(user.isPresent()){
            user.get().getOrderDetailList().stream().forEach(orderDetail -> {
                System.out.println("item : "+orderDetail.getItem());
            });
        }


    }

    @Test
    public void update(){
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(u ->{
            u.setAccount("user01");
            u.setEmail("user01@gmail.com");
            u.setUpdatedAt(LocalDateTime.now());
            u.setUpdatedBy("admin");

            userRepository.save(u);
        });
    }
}
