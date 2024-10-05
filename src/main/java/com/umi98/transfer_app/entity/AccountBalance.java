package com.umi98.transfer_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "account_balance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(name = "balance")
    private Long balance;
    @LastModifiedDate
    @Column(name = "modified_date")
    protected LocalDateTime modifiedDate;
}
