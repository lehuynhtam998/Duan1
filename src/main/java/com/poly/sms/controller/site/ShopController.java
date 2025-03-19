package com.poly.sms.controller.site;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.sms.entity.Category;
import com.poly.sms.entity.Product;
import com.poly.sms.entity.ProductImage;
import com.poly.sms.entity.ProductWithImage;
import com.poly.sms.service.CategoryService;
import com.poly.sms.service.OrderDetailService;
import com.poly.sms.service.ProductImageService;
import com.poly.sms.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("sms")
public class ShopController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    HttpSession session;

    @RequestMapping("shop")
    public String shop(Model model) {
        // // san pham noi bat
        // List<Product> products = orderDetailService.findTopOrderedProducts()
        // .stream()
        // .map(result -> (Product) result[0])
        // .limit(4)
        // .collect(Collectors.toList());

        List<ProductWithImage> producs = topSeller();

        session.setAttribute("sort", true);
        session.setAttribute("discount", false);

        // danh muc san pham init
        session.setAttribute("categoryId", 1);
        PageRequest pageable = PageRequest.of(0, 6);

        Page<Product> pages = productService.findAllByOrderByUnitPriceAsc(pageable);

        List<ProductWithImage> productWithImages = getProductWithImages(pages.getContent());

        List<Category> categories = categoryService.findAll();

        model.addAttribute("productWithImages", productWithImages);
        model.addAttribute("categories", categories);
        model.addAttribute("pages", pages);
        model.addAttribute("products", producs);
        model.addAttribute("selectedCategoryId", 1);
        model.addAttribute("sortOrder", true);
        model.addAttribute("isDiscountChecked", false);
        return "site/shop";
    }

    @RequestMapping("shop/categories")
    public String shopWithCategory(Model model, @RequestParam("categoryId") int id,
            @RequestParam("sort") boolean sort,
            @RequestParam(name = "discount", defaultValue = "false") boolean discount) {
        session.setAttribute("categoryId", id);
        session.setAttribute("sort", sort);
        session.setAttribute("discount", discount);
        PageRequest pageable = PageRequest.of(0, 6);

        Page<Product> pages;
        if (id == 1) {
            // pages = productService.findAllPage(pageable);
            if (discount) {
                if (sort) {
                    pages = productService.findProductsWithDiscountGreaterThanZeroOrderByUnitPriceAsc(pageable);
                } else {
                    pages = productService.findProductsWithDiscountGreaterThanZeroOrderByUnitPriceDesc(pageable);
                }
            } else {
                if (sort) {
                    pages = productService.findAllByOrderByUnitPriceAsc(pageable);
                } else {
                    pages = productService.findAllByOrderByUnitPriceDesc(pageable);
                }
            }
        } else {
            if (discount) {
                if (sort) {
                    pages = productService.findByCategoryIdAndDiscountGreaterThanZeroOrderByUnitPriceAsc(id, pageable);
                } else {
                    pages = productService.findByCategoryIdAndDiscountGreaterThanZeroOrderByUnitPriceDesc(id, pageable);
                }
            } else {
                if (sort) {
                    pages = productService.findByCategoryOrderByUnitPriceAsc(id, pageable);
                } else {
                    pages = productService.findByCategoryOrderByUnitPriceDesc(id, pageable);
                }
            }
        }

        List<ProductWithImage> productWithImages = getProductWithImages(pages.getContent());

        List<Category> categories = categoryService.findAll();

        List<ProductWithImage> producs = topSeller();
        model.addAttribute("products", producs);
        model.addAttribute("productWithImages", productWithImages);
        model.addAttribute("categories", categories);
        model.addAttribute("pages", pages);
        model.addAttribute("selectedCategoryId", id);
        model.addAttribute("sortOrder", sort);
        model.addAttribute("isDiscountChecked", discount);
        return "site/shop";
    }

    @RequestMapping("shop/pages")
    public String shopWithPage(Model model, @RequestParam("p") Optional<Integer> p) {
        Integer id = (Integer) session.getAttribute("categoryId");
        boolean sort = (Boolean) session.getAttribute("sort");
        boolean discount = (Boolean) session.getAttribute("discount");
        PageRequest pageable = PageRequest.of(p.orElse(0), 6);
        Page<Product> pages;
        if (id == 1) {
            // pages = productService.findAllPage(pageable);
            if (discount) {
                if (sort) {
                    pages = productService.findProductsWithDiscountGreaterThanZeroOrderByUnitPriceAsc(pageable);
                } else {
                    pages = productService.findProductsWithDiscountGreaterThanZeroOrderByUnitPriceDesc(pageable);
                }
            } else {
                if (sort) {
                    pages = productService.findAllByOrderByUnitPriceAsc(pageable);
                } else {
                    pages = productService.findAllByOrderByUnitPriceDesc(pageable);
                }
            }
        } else {
            if (discount) {
                if (sort) {
                    pages = productService.findByCategoryIdAndDiscountGreaterThanZeroOrderByUnitPriceAsc(id, pageable);
                } else {
                    pages = productService.findByCategoryIdAndDiscountGreaterThanZeroOrderByUnitPriceDesc(id, pageable);
                }
            } else {
                if (sort) {
                    pages = productService.findByCategoryOrderByUnitPriceAsc(id, pageable);
                } else {
                    pages = productService.findByCategoryOrderByUnitPriceDesc(id, pageable);
                }
            }
        }

        List<ProductWithImage> productWithImages = getProductWithImages(pages.getContent());

        List<Category> categories = categoryService.findAll();

        List<ProductWithImage> producs = topSeller();
        model.addAttribute("products", producs);
        model.addAttribute("productWithImages", productWithImages);
        model.addAttribute("categories", categories);
        model.addAttribute("pages", pages);
        model.addAttribute("selectedCategoryId", id);
        model.addAttribute("sortOrder", sort);
        model.addAttribute("isDiscountChecked", discount);
        return "site/shop";
    }

    private List<ProductWithImage> topSeller() {
        // san pham noi bat
        List<Product> products = orderDetailService.findTopOrderedProducts()
                .stream()
                .map(result -> (Product) result[0])
                .limit(4)
                .collect(Collectors.toList());

        List<ProductWithImage> producs = getProductWithImages(products);

        return producs;
    }

    private List<ProductWithImage> getProductWithImages(List<Product> products) {

        List<ProductImage> productImages = productImageService.findAll()
                .stream()
                .filter(productImage -> products.contains(productImage.getProduct()))
                .collect(Collectors.toList());

        Map<Product, ProductImage> productImageMap = productImages.stream()
                .collect(Collectors.toMap(
                        ProductImage::getProduct,
                        pi -> pi,
                        (existing, replacement) -> existing));

        List<ProductWithImage> productWithImages = products.stream()
                .map(product -> new ProductWithImage(product, productImageMap.get(product)))
                .collect(Collectors.toList());

        return productWithImages;
    }

    // @RequestMapping("shop/keywords")
    // public String shopWithKeywords(Model model, @RequestParam("productName")
    // String productName) {
    // PageRequest pageable = PageRequest.of(0, 4);
    // Page<Product> pages = productService.findByProductNameContaining(productName,
    // pageable);

    // List<ProductWithImage> productWithImages =
    // getProductWithImages(pages.getContent());

    // List<Category> categories = categoryService.findAll();

    // model.addAttribute("productWithImages", productWithImages);
    // model.addAttribute("categories", categories);
    // model.addAttribute("pages", pages);
    // model.addAttribute("selectedTab", "all");
    // return "site/shop";
    // }
}
