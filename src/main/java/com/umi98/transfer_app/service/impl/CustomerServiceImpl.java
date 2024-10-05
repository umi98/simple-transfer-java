package com.umi98.transfer_app.service.impl;

import com.umi98.transfer_app.dto.response.ProfileResponse;
import com.umi98.transfer_app.entity.AccountBalance;
import com.umi98.transfer_app.entity.Customer;
import com.umi98.transfer_app.repository.CustomerRepository;
import com.umi98.transfer_app.service.AccountBalanceService;
import com.umi98.transfer_app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<ProfileResponse> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::mapToResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
//        return mapToResponse(customer);
    }

    @Override
    public ProfileResponse getCustomerById(String id) {
        return mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public Customer getById(String id) {
        return findByIdOrThrowException(id);
    }

    private Customer findByIdOrThrowException(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    private ProfileResponse mapToResponse(Customer customer) {
        return ProfileResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .phone(customer.getPhone())
                .build();
    }
}
