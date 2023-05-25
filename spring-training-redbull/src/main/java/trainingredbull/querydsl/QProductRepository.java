package trainingredbull.querydsl;

import trainingredbull.dto.ProductCustomDTO;
import trainingredbull.entity.Product;

public interface QProductRepository extends BaseRepository<Product, Long>{

    ProductCustomDTO getProductCustomById(Long productId);
}
