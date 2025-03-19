package com.poly.sms.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;


@Data
@Entity
@Table(name = "Employees")
public class Employee {

    @Id
    private String username;

    private String password;

    private String fullname;

    private String email;

    private String phone;

    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<EmployeeRole> employeeRoles;
}
