package com.poly.sms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String sender;

    private String subject;

    private String content;

    private Boolean isRead = false;

    private LocalDateTime receivedDate;
    @PrePersist
    protected void onCreate() {
        this.receivedDate = LocalDateTime.now(); // Thiết lập ngày giờ hiện tại khi tạo entity
    }

    @PreUpdate
    protected void onUpdate() {
        this.receivedDate = LocalDateTime.now(); // Cập nhật ngày giờ hiện tại khi cập nhật entity
    }
}
