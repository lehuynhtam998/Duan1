package com.poly.sms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Integer supplierId;

    @Column(name = "company_name", nullable = false, length = 50, columnDefinition = "NVARCHAR")
    private String companyName;

    @Column(name = "contact_name", nullable = false, length = 50, columnDefinition = "NVARCHAR")
    private String contactName;

    @Column(name = "contact_title", nullable = false, length = 50, columnDefinition = "NVARCHAR")
    private String contactTitle;

    @Column(name = "address", nullable = false, length = 255, columnDefinition = "NVARCHAR")
    private String address;

    @Column(name = "city", nullable = false, length = 20)
    private String city;

    @Column(name = "country", nullable = false, length = 20)
    private String country;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
}
