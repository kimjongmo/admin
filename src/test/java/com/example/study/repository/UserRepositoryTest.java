package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class UserRepositoryTest extends StudyApplicationTests {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void create(){
        String account = "Test03";
        String password = "Test03";
        String status = "REGISTERED";
        String email = "Test03@gmail.com";
        String phoneNumber = "010-3333-3333";
        LocalDateTime registeredAt;

        User user = User.builder()
                .account(account)
                .password(password)
                .status(status)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();

        User savedUser = userRepository.save(user);

        Assert.assertNotNull(savedUser);

    }

    @Test
    @Transactional
    public void read(){
        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");
        user.getOrderGroupList().stream().forEach(orderGroup -> {

            System.out.println("------------주문 묶음-----------");
            System.out.println("수령인 : "+orderGroup.getRevName());
            System.out.println("수령 주소 : "+orderGroup.getRevAddress());
            System.out.println("총액 : "+orderGroup.getTotalPrice());
            System.out.println("총 수량 : "+orderGroup.getTotalQuantity());

            System.out.println("-----------주문 상세--------------");
            orderGroup.getOrderDetailList().stream().forEach(orderDetail -> {
                System.out.println("파트너사 이름 : "+orderDetail.getItem().getPartner().getName());
                System.out.println("주문 카테고리 이름 : "+orderDetail.getItem().getPartner().getCategory().getTitle());
                System.out.println("주문 상품 이름 : "+orderDetail.getItem().getName());
                System.out.println("상태 : "+orderDetail.getStatus());
                System.out.println("도착 예정일 : "+orderDetail.getArrivalDate());
                System.out.println("콜센터 : "+orderDetail.getItem().getPartner().getCallCenter());
            });
        });
        Assert.assertNotNull(user);
    }

    @Test
    public void update(){
//        Optional<User> user = userRepository.findById(2L);
//
//        user.ifPresent(u ->{
//            u.setAccount("user01");
//            u.setEmail("user01@gmail.com");
//            u.setUpdatedAt(LocalDateTime.now());
//            u.setUpdatedBy("admin");
//
//            userRepository.save(u);
//        });
    }
}
