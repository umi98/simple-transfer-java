package com.umi98.transfer_app.service;

import com.umi98.transfer_app.dto.response.ProfileResponse;
import com.umi98.transfer_app.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<ProfileResponse> getAllCustomer();
    Customer addCustomer(Customer customer);
    ProfileResponse getCustomerById(String id);
    Customer getById(String id);
}
