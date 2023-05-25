package trainingredbull.service;

import java.util.List;
import trainingredbull.dto.ProductDTO;

public interface ProductService {

    List<ProductDTO> getProductList();
    
    ProductDTO getProductDetail(Long productId);
    
    Long createProduct(ProductDTO productDTO);
    
    void updateProduct(ProductDTO productDTO);
    
    void deleteProduct(Long productId);
}
