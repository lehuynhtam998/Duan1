package com.poly.sms.controller.app;

import com.poly.sms.dto.ProductQuantityDTO;
import com.poly.sms.entity.OrderDetail;
import com.poly.sms.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.findAll();
    }

    @GetMapping("/OrderId/{id}")
    public List<OrderDetail> getAllOrderDetailsByOrderId(@PathVariable Integer id) {
        return orderDetailService.getAllOrderDetailByOrderID(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Integer id) {
        Optional<OrderDetail> orderDetail = orderDetailService.findById(id);
        if (orderDetail.isPresent()) {
            return ResponseEntity.ok(orderDetail.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<ProductQuantityDTO>> getProductQuantitiesByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<ProductQuantityDTO> productQuantities = orderDetailService.getProductQuantitiesByOrderDate(date);
        return ResponseEntity.ok(productQuantities);
    }

    @PostMapping("/saveAll")
    public List<OrderDetail> saveAllObjects(@RequestBody List<OrderDetail> objects) {
        return orderDetailService.saveAllOrderDetails(objects);
    }

    @PostMapping
    public OrderDetail createOrderDetail(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.save(orderDetail);
    }

    @PostMapping("/bulk")
    public List<OrderDetail> createOrderDetails(@RequestBody List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailService.save(orderDetail);
        }
        return orderDetails;
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable Integer id,
            @RequestBody OrderDetail orderDetailDetails) {
        Optional<OrderDetail> orderDetail = orderDetailService.findById(id);
        if (orderDetail.isPresent()) {
            OrderDetail updatedOrderDetail = orderDetail.get();
            updatedOrderDetail.setQuantity(orderDetailDetails.getQuantity());
            updatedOrderDetail.setPrice(orderDetailDetails.getPrice());
            updatedOrderDetail.setProduct(orderDetailDetails.getProduct());
            updatedOrderDetail.setOrder(orderDetailDetails.getOrder());
            return ResponseEntity.ok(orderDetailService.save(updatedOrderDetail));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Integer id) {
        Optional<OrderDetail> orderDetail = orderDetailService.findById(id);
        if (orderDetail.isPresent()) {
            orderDetailService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
