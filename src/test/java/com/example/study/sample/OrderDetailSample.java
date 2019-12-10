package com.example.study.sample;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import com.example.study.model.enumClass.OrderStatus;
import com.example.study.model.enumClass.OrderGroupStatus;
import com.example.study.model.enumClass.OrderType;
import com.example.study.model.enumClass.PaymentType;
import com.example.study.repository.ItemRepository;
import com.example.study.repository.OrderDetailRepository;
import com.example.study.repository.OrderGroupRepository;
import com.example.study.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class OrderDetailSample extends StudyApplicationTests {

    private Random random = new Random();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Test
    public void createOrder(){

        List<User> userList = userRepository.findAll();

        for(int j = 0; j < 1; j++){
            User user = userList.get(j);
            item(user);

        }


        userList.forEach(user -> {

            int orderCount = random.nextInt(10) + 1;
            for (int i = 0; i < orderCount; i++) {
                item(user);
            }
        });


    }


    private void item(User user){
        double totalAmount = 0;

        Map<Item, Integer> map = new HashMap<>();
        List<Item> items = new ArrayList<>();
        List<OrderDetail> orderHistoryDetails = new ArrayList<>();


        int itemCount = random.nextInt(10)+1;
        for(int i = 0 ; i < itemCount; i ++){
            // db에 아이템 갯수가 총 500개 ( * 자신의 샘플에 맞추세요 )
            int itemNumber = random.nextInt(405)+1;

            Item item = itemRepository.findById((long)itemNumber).get();
            totalAmount += item.getPrice().doubleValue();
            items.add(item);
            if(map.containsKey(item)){
                map.put(item, map.get(item)+1);
            }else{
                map.put(item,1);
            }
        }


        int s = random.nextInt(3)+1;
        OrderStatus status = OrderStatus.DELIVERY;
        PaymentType paymentType = PaymentType.BANK_TRANSFER;
        switch (s){
            case 1 :
                status = OrderStatus.DELIVERY;
                paymentType = PaymentType.BANK_TRANSFER;
                break;

            case 2 :
                status = OrderStatus.COMPLETE;
                paymentType = PaymentType.CARD;
                break;

            case 3 :
                status = OrderStatus.WAITING;
                paymentType = PaymentType.CHECK_CARD;
                break;
        }

        int t = random.nextInt(2)+1;
        OrderType type = t==1? OrderType.ALL:OrderType.EACH;


        OrderGroup orderGroup = OrderGroup.builder()
                .user(user)
                .status(status)
                .orderType(type)
                .revAddress("경기도 분당구 판교역로")
                .revName(user.getEmail())
                .paymentType(paymentType)
                .totalPrice(new BigDecimal(totalAmount))
                .orderAt(getRandomDate())
                .totalQuantity(itemCount)
                .arrivalDate(getRandomDate().plusDays(3))
                .orderDetailList(orderHistoryDetails)
                .build();

        orderGroupRepository.save(orderGroup);



        for(Item item : items){

            OrderStatus orderStatus = OrderStatus.DELIVERY;
            switch (random.nextInt(3)+1){
                case 1 :
                    orderStatus = OrderStatus.DELIVERY;
                    break;

                case 2 :
                    orderStatus = OrderStatus.COMPLETE;
                    break;

                case 3 :
                    orderStatus = OrderStatus.WAITING;
                    break;
            }


            Integer quantity = map.get(item);

            OrderDetail orderDetail = OrderDetail.builder()
                    .orderGroup(orderGroup)
                    .item(item)
                    .quantity(quantity)
                    .totalPrice(item.getPrice().multiply(BigDecimal.valueOf(quantity)))
                    .arrivalDate(type.equals(OrderType.ALL) ? orderGroup.getArrivalDate() : getRandomDate())
                    .status(type.equals(OrderType.ALL) ? status : orderStatus)
                    .build();
            orderDetailRepository.save(orderDetail);
            orderHistoryDetails.add(orderDetail);
        }


    }


    private LocalDateTime getRandomDate(){
        return LocalDateTime.of(2019,getRandomNumber(),getRandomNumber(),getRandomNumber(),getRandomNumber(),getRandomNumber());
    }

    private int getRandomNumber(){
        return random.nextInt(11)+1;
    }
}
