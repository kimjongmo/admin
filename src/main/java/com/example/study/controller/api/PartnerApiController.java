package com.example.study.controller.api;

import com.example.study.controller.CrudController;
import com.example.study.model.entity.Partner;
import com.example.study.model.network.request.partner.PartnerApiRequest;
import com.example.study.model.network.response.partner.PartnerApiResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/partner")
public class PartnerApiController extends CrudController<PartnerApiRequest, PartnerApiResponse, Partner> {
}
