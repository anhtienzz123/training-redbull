package trainingredbull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trainingredbull.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
