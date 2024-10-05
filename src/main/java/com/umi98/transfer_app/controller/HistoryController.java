package com.umi98.transfer_app.controller;

import com.umi98.transfer_app.dto.response.HistoryResponse;
import com.umi98.transfer_app.service.HistoryService;
import com.umi98.transfer_app.utils.ResponseBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;
    private final ResponseBuilderUtil responseBuilderUtil;

    @GetMapping
    public ResponseEntity<?> getAllHistory() {
        List<HistoryResponse> responses = historyService.getAllHistory();
        return responseBuilderUtil.buildResponse(responses, "Data fetched", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHistoryById(@PathVariable String id) {
        List<HistoryResponse> responses = historyService.listOfHistoryByUserId(id);
        return responseBuilderUtil.buildResponse(responses, "Data fetched", HttpStatus.OK);
    }
}
