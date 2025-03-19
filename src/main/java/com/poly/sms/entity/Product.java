package com.poly.sms.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
    
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "product_name", nullable = false, length = 50, columnDefinition = "NVARCHAR")
    private String productName;

    @Column(name = "quantity", nullable = true)
    private Integer quantity;

    @Column(name = "unit_price", nullable = true, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "import_price", nullable = true, precision = 10, scale = 2)
    private BigDecimal giaNhap;

    @Column(name = "product_status", nullable = false)
    private Integer productStatus;

    @Column(name = "discount", nullable = false, precision = 5, scale = 2)
    private BigDecimal discount;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "expiry_duration ")
    private Integer expiryDuration ;

    @Column(name = "manufacture_date ")
    private Date manufactureDate ;

    @Column(name = "day_added ")
    private Date dayAdded ;

}
