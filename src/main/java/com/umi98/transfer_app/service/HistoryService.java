package com.umi98.transfer_app.service;

import com.umi98.transfer_app.dto.response.HistoryResponse;
import com.umi98.transfer_app.entity.History;

import java.util.List;

public interface HistoryService {
    void addHistory(History history);
    List<HistoryResponse> listOfHistoryByUserId(String userId);
    List<HistoryResponse> getAllHistory();
}
