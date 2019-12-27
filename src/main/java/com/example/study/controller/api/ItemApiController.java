package com.example.study.controller.api;


import com.example.study.controller.CrudController;
import com.example.study.model.entity.Item;
import com.example.study.model.network.request.item.ItemApiRequest;
import com.example.study.model.network.response.item.ItemApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/item")
@CrossOrigin
public class ItemApiController extends CrudController<ItemApiRequest, ItemApiResponse, Item> {

}
