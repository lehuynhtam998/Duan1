package com.poly.sms.service;

import com.poly.sms.entity.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchService {

    List<Branch> findAll();

    Optional<Branch> findById(Integer id);

    Branch save(Branch branch);

    void deleteById(Integer id);
    
    List<Branch> getBranchIdByManagerUsername(String username);
}
