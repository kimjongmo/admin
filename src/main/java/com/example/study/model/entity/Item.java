package com.example.study.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    private String content;

    @OneToMany(mappedBy = "item",fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetailList;
}
