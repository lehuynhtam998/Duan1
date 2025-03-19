package com.poly.sms.service;

import com.poly.sms.entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {

    List<Supplier> findAll();

    Optional<Supplier> findById(Integer id);

    Supplier save(Supplier supplier);

    void deleteById(Integer id);
}
