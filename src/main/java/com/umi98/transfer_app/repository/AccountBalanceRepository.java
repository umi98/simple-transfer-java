package com.umi98.transfer_app.repository;

import com.umi98.transfer_app.entity.AccountBalance;
import com.umi98.transfer_app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance, String> {
    Optional<AccountBalance> findByCustomer(Customer customer);
}
