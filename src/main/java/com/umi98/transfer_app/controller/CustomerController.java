package com.umi98.transfer_app.controller;

import com.umi98.transfer_app.dto.response.ProfileResponse;
import com.umi98.transfer_app.service.CustomerService;
import com.umi98.transfer_app.utils.ResponseBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final ResponseBuilderUtil responseBuilderUtil;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllCustomer() {
        List<ProfileResponse> response = customerService.getAllCustomer();
        return responseBuilderUtil.buildResponse(response, "Data fetched", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        ProfileResponse response = customerService.getCustomerById(id);
        return responseBuilderUtil.buildResponse(response, "Data fetched", HttpStatus.OK);
    }


}
