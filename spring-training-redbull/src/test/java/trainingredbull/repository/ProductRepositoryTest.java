package trainingredbull.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import trainingredbull.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void givenProductList_whenFindAll_thenProductList() {
        // given
        Product product1 = Product.builder().name("product 1").price(100L).build();

        Product product2 = Product.builder().name("product 2").price(200L).build();

        productRepository.save(product1);
        productRepository.save(product2);

        // when
        List<Product> productList = productRepository.findAll();

        // then
        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(2);

    }


    @Test
    public void givenProductObject_whenFindById_thenReturnProductObject() {
        // given
        Product product = Product.builder().name("product ").price(100L).build();
        productRepository.save(product);

        // when
        Product productDB = productRepository.findById(product.getId()).get();

        // then
        assertThat(productDB).isNotNull();
    }

    @Test
    public void givenProductObject_whenSave_thenReturnSavedProduct() {
        // given
        Product product = Product.builder().name("product 1").price(1L).build();

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void givenProductObject_whenUpdateProduct_thenReturnUpdatedProduct() {
        // given
        Product product = Product.builder().name("product 1").price(1L).build();
        productRepository.save(product);

        // when
        Product savedProduct = productRepository.findById(product.getId()).get();
        savedProduct.setName("product 2");
        savedProduct.setPrice(2L);
        Product updatedProduct = productRepository.save(savedProduct);

        // then
        assertThat(updatedProduct.getName()).isEqualTo("product 2");
        assertThat(updatedProduct.getPrice()).isEqualTo(2L);
    }

    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){
        // given
        Product product = Product.builder().name("product 1").price(1L).build();
        productRepository.save(product);

        // when
        productRepository.deleteById(product.getId());
        Optional<Product> productOptional = productRepository.findById(product.getId());

        // then
        assertThat(productOptional).isEmpty();
    }
}
