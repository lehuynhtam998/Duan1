package com.poly.sms.controller.app;

import com.poly.sms.entity.Supplier;
import com.poly.sms.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Integer id) {
        Optional<Supplier> supplier = supplierService.findById(id);
        if (supplier.isPresent()) {
            return ResponseEntity.ok(supplier.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Integer id, @RequestBody Supplier supplierDetails) {
        Optional<Supplier> supplier = supplierService.findById(id);
        if (supplier.isPresent()) {
            Supplier updatedSupplier = supplier.get();
            updatedSupplier.setCompanyName(supplierDetails.getCompanyName());
            updatedSupplier.setContactName(supplierDetails.getContactName());
            updatedSupplier.setContactTitle(supplierDetails.getContactTitle());
            updatedSupplier.setAddress(supplierDetails.getAddress());
            updatedSupplier.setCity(supplierDetails.getCity());
            updatedSupplier.setCountry(supplierDetails.getCountry());
            updatedSupplier.setPhone(supplierDetails.getPhone());
            return ResponseEntity.ok(supplierService.save(updatedSupplier));
        } else {
            return ResponseEntity.notFound().build();
        }
        // return ResponseEntity.ok(supplierService.save(supplierDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Integer id) {
        Optional<Supplier> supplier = supplierService.findById(id);
        if (supplier.isPresent()) {
            supplierService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
