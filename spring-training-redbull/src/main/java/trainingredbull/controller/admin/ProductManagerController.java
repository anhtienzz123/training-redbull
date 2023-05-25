package trainingredbull.controller.admin;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trainingredbull.dto.ProductCustomDTO;
import trainingredbull.dto.ProductDTO;
import trainingredbull.querydsl.QProductRepository;
import trainingredbull.service.ProductService;

@RestController
@RequestMapping("/admin/products")
@Slf4j
@AllArgsConstructor
public class ProductManagerController {

    private final ProductService productService;
    
    private final QProductRepository qProductRepository;
    
    @GetMapping("/custom/{productId}")
    public ProductCustomDTO getProductCustom(@PathVariable("productId") Long productId) {
        log.debug("IN - getProductCustom: {}", productId);
        return qProductRepository.getProductCustomById(productId);
    }
    
    @GetMapping
    public List<ProductDTO> getProductList() {
        log.debug("IN - getProductList");
        return productService.getProductList();
    }

    @GetMapping("/{productId}")
    public ProductDTO getProductDetail(@PathVariable("productId") Long productId) {
        log.debug("IN - getProductDetail: {}", productId);
        return productService.getProductDetail(productId);
    }

    @PostMapping
    public Long createProduct(@RequestBody ProductDTO productDTO) {
        log.debug("IN - createProduct: {}", productDTO);
        return productService.createProduct(productDTO);
    }

    @PutMapping
    public void updateProduct(@RequestBody ProductDTO productDTO) {
        log.debug("IN - updateProduct: {}", productDTO);
        productService.updateProduct(productDTO);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        log.debug("IN - deleteProduct: {}", productId);
        productService.deleteProduct(productId);
    }
}
