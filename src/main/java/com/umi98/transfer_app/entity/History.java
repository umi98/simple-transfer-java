package com.umi98.transfer_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_seq")
    @SequenceGenerator(name = "history_seq", sequenceName = "history_seq", allocationSize = 1)
    private int id;
    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
    @Column(name = "description")
    private String description;
    @CreatedDate
    @Column(name = "activity_time", updatable = false)
    private LocalDateTime activityTime;
}
