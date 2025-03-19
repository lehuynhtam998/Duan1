package com.poly.sms.controller.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.sms.service.OrderService;
//Phần code của Tâm

@Controller
@RequestMapping("sms")
public class OrderHistoryController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("orderhistory")
    public String orderhistory() {
        return "site/orderhistory";
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId) {
        orderService.updateOrderStatusToCancelled(orderId);
        return ResponseEntity.ok("Đơn hàng đã được hủy.");
    }
    

}
//