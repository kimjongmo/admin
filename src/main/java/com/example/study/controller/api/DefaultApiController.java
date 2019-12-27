package com.example.study.controller.api;

import com.example.study.model.network.Header;
import com.example.study.model.network.response.defaultApi.DefaultApiResponse;
import com.example.study.service.defaultApi.DefaultApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/default")
@CrossOrigin
public class DefaultApiController {

    @Autowired
    private DefaultApiLogicService defaultApiLogicService;

    @GetMapping("/count")
    public Header<DefaultApiResponse> count(){
        return defaultApiLogicService.count();
    }

}
