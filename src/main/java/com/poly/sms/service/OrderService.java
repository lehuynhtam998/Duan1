package com.poly.sms.service;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.sms.entity.Order;

public interface OrderService {
    Order update(Order order);

    Order updateProdOrder(Order order);

    List<Order> getOrdersHistory(String seller);
    
    List<Order> getOrdersHoaDon();

    List<Order> getOrdersNhapHang();

    List<Order> getOrdersDatHang();

    List<Order> getOrdersChuyenHang();

    List<Order> findAll();

    Optional<Order> findById(Integer id);

    Order create(JsonNode orderData);

    Order save(Order order);

    void deleteById(Integer id);

    long countOrdersWithStatus1();

    List<Object[]> getMonthlyRevenueForYear(int year);

    List<Object[]> countSuccessfulOrdersByMonthForYear(int year);
    
    List<Object[]> getProductRevenueByMonthForYear(int year);

    List<Object[]> getTotalPriceByMonth(int year);

    int updateOrderStatusToCancelled(Integer orderId);
}
