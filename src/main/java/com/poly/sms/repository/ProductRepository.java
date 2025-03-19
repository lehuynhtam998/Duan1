package com.poly.sms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.sms.entity.Branch;
import com.poly.sms.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

        List<Product> findByBranch(Branch branch);

        List<Product> findTop4ByOrderByDiscountDesc();

        Product findByProductId(Integer productId);

        @Query("SELECT p FROM Product p WHERE p.category.categoryId = ?1")
        Page<Product> findProductsByCategoryId(int keywords, Pageable pageable);

        @Query("SELECT p FROM Product p WHERE p.category.categoryId = ?1")
        List<Product> findProductsByCategoryId(int keywords);

        @Query("SELECT p FROM Product p WHERE p.productName LIKE %?1%")
        Page<Product> findProductByKeywords(String keywords, Pageable pageable);

        @Query("SELECT p FROM Product p WHERE p.productName LIKE %?1%")
        Page<Product> findByProductNameContaining(String productName, Pageable pageable);

        // Quan ly danh muc san pham

        Page<Product> findAllByOrderByUnitPriceDesc(Pageable pageable);

        Page<Product> findAllByOrderByUnitPriceAsc(Pageable pageable);

        // Lọc các sản phẩm với discount lớn hơn 0 và sắp xếp giảm dần theo unitPrice
        @Query("SELECT p FROM Product p WHERE p.discount > 0 ORDER BY p.unitPrice DESC")
        Page<Product> findProductsWithDiscountGreaterThanZeroOrderByUnitPriceDesc(Pageable pageable);

        // Lọc các sản phẩm với discount lớn hơn 0 và sắp xếp tăng dần theo unitPrice
        @Query("SELECT p FROM Product p WHERE p.discount > 0 ORDER BY p.unitPrice ASC")
        Page<Product> findProductsWithDiscountGreaterThanZeroOrderByUnitPriceAsc(Pageable pageable);

        // Lọc theo categoryId và sắp xếp giảm dần theo unitPrice
        @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId ORDER BY p.unitPrice DESC")
        Page<Product> findByCategoryOrderByUnitPriceDesc(@Param("categoryId") int categoryId, Pageable pageable);

        // Lọc theo categoryId và sắp xếp tăng dần theo unitPrice
        @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId ORDER BY p.unitPrice ASC")
        Page<Product> findByCategoryOrderByUnitPriceAsc(@Param("categoryId") int categoryId, Pageable pageable);

        // Lọc theo categoryId và discount lớn hơn 0, sau đó sắp xếp giảm dần theo
        // unitPrice
        @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId AND p.discount > 0 ORDER BY p.unitPrice DESC")
        Page<Product> findByCategoryIdAndDiscountGreaterThanZeroOrderByUnitPriceDesc(
                        @Param("categoryId") int categoryId,
                        Pageable pageable);

        // Lọc theo categoryId và discount lớn hơn 0, sau đó sắp xếp tăng dần theo
        // unitPrice
        @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId AND p.discount > 0 ORDER BY p.unitPrice ASC")
        Page<Product> findByCategoryIdAndDiscountGreaterThanZeroOrderByUnitPriceAsc(@Param("categoryId") int categoryId,
                        Pageable pageable);

        // @Query(value = "SELECT p.product_id, p.product_name, it.manufacturing_date, "
        // +
        // "DATE_ADD(it.manufacturing_date, INTERVAL p.expiry_duration DAY) AS
        // expiry_date, " +
        // "DATEDIFF(DATE_ADD(it.manufacturing_date, INTERVAL p.expiry_duration DAY),
        // CURRENT_DATE()) AS days_left, " +
        // "SUM(itd.quantity) AS total_quantity " +
        // "FROM products p " +
        // "JOIN inventory_transaction_details itd ON p.product_id = itd.product_id " +
        // "JOIN inventory_transactions it ON itd.inventory_transaction_id =
        // it.inventory_transaction_id " +
        // "WHERE DATEDIFF(DATE_ADD(it.manufacturing_date, INTERVAL p.expiry_duration
        // DAY), CURRENT_DATE()) <= :days " +
        // "GROUP BY p.product_id, it.manufacturing_date " +
        // "HAVING MIN(DATEDIFF(DATE_ADD(it.manufacturing_date, INTERVAL
        // p.expiry_duration DAY), CURRENT_DATE())) <= :days " +
        // "ORDER BY days_left", nativeQuery = true)
        // List<Object[]> findExpiringProducts(@Param("days") int days);
        @Query("SELECT MONTH(p.dayAdded) AS month, SUM(p.giaNhap * p.quantity) AS total "
                        + "FROM Product p "
                        + "WHERE YEAR(p.dayAdded) = :year "
                        + "GROUP BY MONTH(p.dayAdded)")
        List<Object[]> getTotalCostByMonth(@Param("year") int year);

}
