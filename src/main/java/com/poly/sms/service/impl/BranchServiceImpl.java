package com.poly.sms.service.impl;

import com.poly.sms.entity.Branch;
import com.poly.sms.repository.BranchRepository;
import com.poly.sms.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    @Override
    public Optional<Branch> findById(Integer id) {
        return branchRepository.findById(id);
    }

    @Override
    public Branch save(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public void deleteById(Integer id) {
        branchRepository.deleteById(id);
    }

    @Override
    public List<Branch> getBranchIdByManagerUsername(String username) {
        return branchRepository.findBranchByManagerBranchUsername(username);
    }
}
