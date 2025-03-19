package com.poly.sms.service.impl;

import com.poly.sms.dto.ProductQuantityDTO;
import com.poly.sms.entity.OrderDetail;
import com.poly.sms.repository.OrderDetailRepository;
import com.poly.sms.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> findById(Integer id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteById(Integer id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<Object[]> findTopOrderedProducts() {
        return orderDetailRepository.findTopOrderedProducts();
    }

    @Override
    public List<OrderDetail> getAllOrderDetailByOrderID(Integer id) {
        return orderDetailRepository.findByOrderOrderId(id);
    }

    @Override
    public List<OrderDetail> saveAllOrderDetails(List<OrderDetail> orderDetails) {
        return orderDetailRepository.saveAll(orderDetails);
    }

    @Override
    public List<ProductQuantityDTO> getProductQuantitiesByOrderDate(Date date) {
        return orderDetailRepository.findProductQuantitiesByOrderDate(date);
    }
}
