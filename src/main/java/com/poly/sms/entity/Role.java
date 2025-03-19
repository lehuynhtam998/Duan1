package com.poly.sms.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Roles")
public class Role {

    @Id
    private String roleId;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<EmployeeRole> employeeRoles;
}
