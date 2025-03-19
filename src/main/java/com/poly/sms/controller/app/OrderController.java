package com.poly.sms.controller.app;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.sms.entity.Order;
import com.poly.sms.service.OrderService;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/updateProd")
    public ResponseEntity<Order> createOrUpdateOrder(@RequestBody Order order) {
        Order savedOrder = orderService.updateProdOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/hoaDon")
    public List<Order> getAllOrdersHoaDon() {
        return orderService.getOrdersHoaDon();
    }

    @GetMapping("/orderChuaXL")
    public long getOrderChuaXuLy() {
        return orderService.countOrdersWithStatus1();
    }

    @GetMapping("/{id}/history")
    public List<Order> getAllOrdersHistory(@PathVariable String id) {
        return orderService.getOrdersHistory(id);
    }

    @GetMapping("/nhapHang")
    public List<Order> getAllOrdersNhapHang() {
        return orderService.getOrdersNhapHang();
    }

    @GetMapping("/datHang")
    public List<Order> getAllOrdersDatHang() {
        return orderService.getOrdersDatHang();
    }

    @GetMapping("/chuyenHang")
    public List<Order> getAllOrdersChuyenHang() {
        return orderService.getOrdersChuyenHang();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        Optional<Order> order = orderService.findById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Order createOrder(@RequestBody JsonNode orderData) {
        return orderService.create(orderData);
    }
    @PutMapping("/updateTotal/{id}")
    public Order updateTotal(@PathVariable Integer id,@RequestBody Order order) {
        return orderService.update(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order orderDetails) {
        Optional<Order> order = orderService.findById(id);
        if (order.isPresent()) {
            Order updatedOrder = order.get();
            updatedOrder.setOrderType(orderDetails.getOrderType());
            updatedOrder.setOrderDate(orderDetails.getOrderDate());
            updatedOrder.setTotalPrice(orderDetails.getTotalPrice());
            updatedOrder.setShipAddress(orderDetails.getShipAddress());
            updatedOrder.setOrderStatus(orderDetails.getOrderStatus());
            updatedOrder.setComments(orderDetails.getComments());
            updatedOrder.setBranch(orderDetails.getBranch());
            return ResponseEntity.ok(orderService.save(updatedOrder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        Optional<Order> order = orderService.findById(id);
        if (order.isPresent()) {
            orderService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/monthly-revenue")
    public ResponseEntity<List<Object[]>> getMonthlyRevenueForYear(@RequestParam int year) {
        return ResponseEntity.ok(orderService.getMonthlyRevenueForYear(year));
    }

    @GetMapping("/successful-orders")
    public ResponseEntity<List<Object[]>> getSuccessfulOrdersByMonthForYear(@RequestParam int year) {
        return ResponseEntity.ok(orderService.countSuccessfulOrdersByMonthForYear(year));
    }

    @GetMapping("/product-revenue")
    public ResponseEntity<List<Object[]>> getProductRevenueByMonthForYear(@RequestParam int year) {
        return ResponseEntity.ok(orderService.getProductRevenueByMonthForYear(year));
    }

    @GetMapping("/total-price-by-month")
    public List<Object[]> getTotalPriceByMonth(@RequestParam int year) {
        return orderService.getTotalPriceByMonth(year);
    }
}
