package com.umi98.transfer_app.service.impl;

import com.umi98.transfer_app.dto.response.HistoryResponse;
import com.umi98.transfer_app.entity.AppUser;
import com.umi98.transfer_app.entity.Customer;
import com.umi98.transfer_app.entity.History;
import com.umi98.transfer_app.repository.HistoryRepository;
import com.umi98.transfer_app.service.AppUserService;
import com.umi98.transfer_app.service.CustomerService;
import com.umi98.transfer_app.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;
    private final AppUserService appUserService;

    @Override
    public void addHistory(History history) {
        historyRepository.save(history);
    }

    @Override
    public List<HistoryResponse> listOfHistoryByUserId(String userId) {
        AppUser user = appUserService.getById(userId);
        List<History> result = historyRepository.findByAppUser(user);
        List<HistoryResponse> responses = new ArrayList<>();
        for (History r : result) {
            HistoryResponse response = HistoryResponse.builder()
                    .customerId(r.getAppUser().getId())
                    .description(r.getDescription())
                    .date(r.getActivityTime())
                    .build();
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<HistoryResponse> getAllHistory() {
        List<History> result = historyRepository.findAll();
        List<HistoryResponse> responses = new ArrayList<>();
        for (History r : result) {
            HistoryResponse response = HistoryResponse.builder()
                    .customerId(r.getAppUser().getId())
                    .description(r.getDescription())
                    .date(r.getActivityTime())
                    .build();
            responses.add(response);
        }
        return responses;
    }
}
