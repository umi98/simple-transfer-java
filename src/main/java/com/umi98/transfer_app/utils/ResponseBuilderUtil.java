package com.umi98.transfer_app.utils;

import com.umi98.transfer_app.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilderUtil {
    public <T>ResponseEntity<CommonResponse<T>> buildResponse(T data, String message, HttpStatus status) {
        CommonResponse<T> response = CommonResponse.<T>builder()
                .message(message)
                .status(status.value())
                .data(data)
                .build();
        return ResponseEntity
                .status(status)
                .body(response);
    }
}
