package com.poly.sms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Branchs")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Integer branchId;

    @Column(name = "branch_type", nullable = false, length = 10)
    private String branchType;

    @Column(name = "branch_name", nullable = false, length = 50, columnDefinition = "NVARCHAR")
    private String branchName;

    @Column(name = "manager_name", nullable = false, length = 50, columnDefinition = "NVARCHAR")
    private String nguoiTao;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "branch_state", nullable = false)
    private Integer branchState;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private Employee managerBranch;
}
