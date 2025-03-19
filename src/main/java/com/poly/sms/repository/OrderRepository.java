package com.poly.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.sms.entity.Order;

import jakarta.transaction.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // List<Order> findByOrderTypeAndOrderStatus(String orderType, Integer
    // orderStatus);
    Order findByOrderId(Integer orderId);

    long countByOrderStatus(Integer orderStatus);

    @Query("SELECT o FROM Order o WHERE o.orderType = :orderType AND o.orderStatus = :orderStatus")
    List<Order> findOrdersByTypeAndStatus(@Param("orderType") String orderType,
            @Param("orderStatus") Integer orderStatus);

    @Query("SELECT o FROM Order o WHERE o.orderStatus = :status1 OR o.orderStatus = :status2")
    List<Order> findOrdersByStatus(@Param("status1") Integer status1, @Param("status2") Integer status2);

    @Query("SELECT o FROM Order o WHERE o.orderType = :orderType")
    List<Order> findOrdersByType(@Param("orderType") String orderType);

    @Query("SELECT o FROM Order o WHERE o.seller = :seller")
    List<Order> findOrdersUsername(@Param("seller") String seller);

    @Query("SELECT MONTH(o.orderDate) AS month, YEAR(o.orderDate) AS year, SUM(o.totalPrice) FROM Order o WHERE YEAR(o.orderDate) = :year GROUP BY MONTH(o.orderDate), YEAR(o.orderDate)")
    List<Object[]> getMonthlyRevenueForYear(@Param("year") int year);

    @Query("SELECT MONTH(o.orderDate) AS month, YEAR(o.orderDate) AS year, COUNT(o) FROM Order o WHERE o.orderStatus = 3 AND YEAR(o.orderDate) = :year GROUP BY MONTH(o.orderDate), YEAR(o.orderDate)")
    List<Object[]> countSuccessfulOrdersByMonthForYear(@Param("year") int year);

    @Query("SELECT MONTH(od.order.orderDate) AS month, YEAR(od.order.orderDate) AS year, od.product.productName, SUM(od.price * od.quantity) FROM OrderDetail od WHERE YEAR(od.order.orderDate) = :year GROUP BY MONTH(od.order.orderDate), YEAR(od.order.orderDate), od.product.productName")
    List<Object[]> getProductRevenueByMonthForYear(@Param("year") int year);

    @Query("SELECT MONTH(o.orderDate) AS month, SUM(o.totalPrice) AS total FROM Order o WHERE YEAR(o.orderDate) = :year AND o.orderType = 'đơn nhập' GROUP BY MONTH(o.orderDate)")
    List<Object[]> getTotalPriceByMonth(@Param("year") int year);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.orderStatus = 4 WHERE o.orderId = :orderId")
    int updateOrderStatusToCancelled(@Param("orderId") Integer orderId);

}
