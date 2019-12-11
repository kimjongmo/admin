package com.example.study.controller.api;


import com.example.study.controller.CrudController;
import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.Item;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.item.ItemApiRequest;
import com.example.study.model.network.response.item.ItemApiResponse;
import com.example.study.service.item.ItemApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/item")
@CrossOrigin(origins = {"http://localhost:8080"})
public class ItemApiController extends CrudController<ItemApiRequest, ItemApiResponse, Item> {

}
