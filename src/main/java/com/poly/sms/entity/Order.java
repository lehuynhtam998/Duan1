package com.poly.sms.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_transactions")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_transaction_id")
    private Integer orderId;

    @Column(name = "transaction_type", nullable = false, length = 10)
    private String orderType;

    @Column(name = "creation_date", nullable = false)
    private Date orderDate;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "ship_address", nullable = false, length = 100, columnDefinition = "NVARCHAR")
    private String shipAddress;

    @Column(name = "created_by", nullable = false, length = 100, columnDefinition = "NVARCHAR")
    private String seller;

    @Column(name = "transaction_status", nullable = false)
    private Integer orderStatus;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "sales_channel", columnDefinition = "TEXT")
    private String sales_channel;

    @Column(name = "recipient_name", columnDefinition = "TEXT")
    private String nguoiNhan;

    @Column(name = "recipient_phone", columnDefinition = "TEXT")
    private String sdtNguoiNhan;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails;

}
