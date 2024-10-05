package com.umi98.transfer_app.controller;

import com.umi98.transfer_app.dto.request.SaveRequest;
import com.umi98.transfer_app.dto.request.TransferMoneyRequest;
import com.umi98.transfer_app.dto.response.BalanceResponse;
import com.umi98.transfer_app.service.AccountBalanceService;
import com.umi98.transfer_app.utils.ResponseBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final AccountBalanceService accountBalanceService;
    private final ResponseBuilderUtil responseBuilderUtil;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBalanceById(@PathVariable String id) {
        BalanceResponse response = accountBalanceService.checkBalance(id);
        return responseBuilderUtil.buildResponse(response, "Data fetched", HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferMoneyRequest request) {
        BalanceResponse response = accountBalanceService.transferMoney(request);
        return responseBuilderUtil.buildResponse(response, "Successfully send money", HttpStatus.OK);
    }

    @PostMapping("/account")
    public ResponseEntity<?> createAccount(@RequestBody SaveRequest request) {
        BalanceResponse response = accountBalanceService.addBalanceAccount(request);
        return responseBuilderUtil.buildResponse(response, "Successfully create account", HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMoney(@RequestBody SaveRequest request) {
        BalanceResponse response = accountBalanceService.saveMoney(request);
        return responseBuilderUtil.buildResponse(response, "Successfully save money", HttpStatus.OK);
    }
}
