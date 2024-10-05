package com.umi98.transfer_app.service.impl;

import com.umi98.transfer_app.dto.request.SaveRequest;
import com.umi98.transfer_app.dto.request.TransferMoneyRequest;
import com.umi98.transfer_app.dto.response.BalanceResponse;
import com.umi98.transfer_app.entity.AccountBalance;
import com.umi98.transfer_app.entity.AppUser;
import com.umi98.transfer_app.entity.Customer;
import com.umi98.transfer_app.entity.History;
import com.umi98.transfer_app.repository.AccountBalanceRepository;
import com.umi98.transfer_app.service.AccountBalanceService;
import com.umi98.transfer_app.service.AppUserService;
import com.umi98.transfer_app.service.CustomerService;
import com.umi98.transfer_app.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountBalanceServiceImpl implements AccountBalanceService {
    private final AccountBalanceRepository accountBalanceRepository;
    private final CustomerService customerService;
    private final AppUserService appUserService;
    private final HistoryService historyService;

    @Override
    public BalanceResponse addBalanceAccount(SaveRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());
        AccountBalance accountBalance = AccountBalance.builder()
                .customer(customer)
                .balance(0L)
                .modifiedDate(LocalDateTime.now())
                .build();
        accountBalanceRepository.save(accountBalance);
        AppUser user = appUserService.getById(customer.getAppUser().getId());
        History history = History.builder()
                .appUser(user)
                .description("User "+user.getId()+" created an account with id "+accountBalance.getId())
                .activityTime(LocalDateTime.now())
                .build();
        historyService.addHistory(history);
        return mapToResponse(accountBalance);
    }

    @Override
    public BalanceResponse checkBalance(String customerId) {
        try {
            Customer customer = customerService.getById(customerId);
            AppUser user = appUserService.getById(customer.getAppUser().getId());
            AccountBalance balance = findOrThrowException(customerId);
            History history = History.builder()
                    .appUser(user)
                    .description("User "+user.getId()+" check the balance of his account: "+balance.getId())
                    .activityTime(LocalDateTime.now())
                    .build();
            historyService.addHistory(history);
            return mapToResponse(balance);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BalanceResponse transferMoney(TransferMoneyRequest request) {
        AccountBalance accountBalance = findOrThrowException(request.getSenderCustomerId());
        AccountBalance destination = findOrThrowException(request.getDestinationCustomerId());
        if (accountBalance.getBalance() > request.getNominal()){
            accountBalance.setBalance(accountBalance.getBalance() - request.getNominal());
            destination.setBalance(destination.getBalance() + request.getNominal());
            AppUser sender = appUserService.getById(accountBalance.getCustomer().getAppUser().getId());
            AppUser recipient = appUserService.getById(destination.getCustomer().getAppUser().getId());
            History history = History.builder()
                    .appUser(sender)
                    .description("User "+sender.getId()+" send money to "+recipient.getId()+"totaling: "+request.getNominal())
                    .activityTime(LocalDateTime.now())
                    .build();
            accountBalanceRepository.save(accountBalance);
            accountBalanceRepository.save(destination);
            historyService.addHistory(history);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough money");
        }
        return mapToResponse(accountBalance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BalanceResponse saveMoney(SaveRequest request) {
        try {
            AccountBalance accountBalance = findOrThrowException(request.getCustomerId());
            Long newNominal = accountBalance.getBalance() + request.getNominal();
            accountBalance.setBalance(newNominal);
            accountBalanceRepository.save(accountBalance);
            Customer user = customerService.getById(request.getCustomerId());
            History history = History.builder()
                    .appUser(user.getAppUser())
                    .description("User "+user.getAppUser().getId()+" deposited money, nominal: "+request.getNominal())
                    .activityTime(LocalDateTime.now())
                    .build();
            historyService.addHistory(history);
            return mapToResponse(accountBalance);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private AccountBalance findOrThrowException(String id) {
        Customer customer = customerService.getById(id);
        Optional<AccountBalance> accountBalance = accountBalanceRepository.findByCustomer(customer);
        return accountBalance.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    private BalanceResponse mapToResponse(AccountBalance accountBalance) {
        return BalanceResponse.builder()
                .customerId(accountBalance.getCustomer().getId())
                .balance(accountBalance.getBalance())
                .lastModified(accountBalance.getModifiedDate())
                .build();
    }
}
