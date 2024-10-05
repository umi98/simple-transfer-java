package com.umi98.transfer_app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferMoneyRequest {
    private String senderCustomerId;
    private String destinationCustomerId;
    private Long nominal;
}
