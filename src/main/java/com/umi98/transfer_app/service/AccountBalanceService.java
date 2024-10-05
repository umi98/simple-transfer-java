package com.umi98.transfer_app.service;

import com.umi98.transfer_app.dto.request.SaveRequest;
import com.umi98.transfer_app.dto.request.TransferMoneyRequest;
import com.umi98.transfer_app.dto.response.BalanceResponse;
import com.umi98.transfer_app.entity.AccountBalance;

public interface AccountBalanceService {
    BalanceResponse addBalanceAccount(SaveRequest saveRequest);
    BalanceResponse checkBalance(String customerId);
    BalanceResponse transferMoney(TransferMoneyRequest request);
    BalanceResponse saveMoney(SaveRequest request);
}
