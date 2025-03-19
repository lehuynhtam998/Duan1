package com.poly.sms.repository;

import com.poly.sms.entity.Branch;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    @Query("SELECT b FROM Branch b WHERE b.managerBranch.username = :username")
    List<Branch> findBranchByManagerBranchUsername(@Param("username") String username);

    Branch findByBranchId(Integer branchId);
}
