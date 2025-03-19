package com.poly.sms.service;

import com.poly.sms.dto.ProductQuantityDTO;
import com.poly.sms.entity.OrderDetail;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderDetailService {

    List<OrderDetail> saveAllOrderDetails(List<OrderDetail> orderDetails);

    List<OrderDetail> getAllOrderDetailByOrderID(Integer id);

    List<OrderDetail> findAll();

    List<ProductQuantityDTO> getProductQuantitiesByOrderDate(Date date);

    Optional<OrderDetail> findById(Integer id);

    OrderDetail save(OrderDetail orderDetail);

    void deleteById(Integer id);

    List<Object[]> findTopOrderedProducts();
}
