package com.poly.sms.repository;

import com.poly.sms.entity.Employee;
import com.poly.sms.entity.EmployeeRole;
import com.poly.sms.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Integer> {
    List<EmployeeRole> findByEmployee(Employee employee);
    List<EmployeeRole> findByRole(Role role);

    @Query("SELECT er.employee FROM EmployeeRole er WHERE er.role.id = 'USER'")
    List<Employee> findEmployeesByRoleName(String roleName);
}
