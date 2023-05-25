package trainingredbull.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import trainingredbull.dto.ProductDTO;
import trainingredbull.entity.Product;
import trainingredbull.querydsl.QProductRepository;
import trainingredbull.service.impl.ProductServiceImpl;

@ExtendWith(value = {MockitoExtension.class, SpringExtension.class})
public class ProductServiceTest {

    @Mock
    private QProductRepository qProductRepository; 
    
    @MockBean
    private ModelMapper modelMapper;
    
    @InjectMocks
    private ProductServiceImpl productService;
    
    private Product productData;

    private ProductDTO productDTOData;
    
    @BeforeEach
    public void setup() {
        productData = Product.builder().id(1L).name("product test").price(100L).build();
        
        productDTOData = ProductDTO.builder().id(1L).name("product dto test").price(100L).build();
    }

    @Test
    public void givenProductList_whenGetProductList_thenReturnProductList() {

        // given
        Mockito.when(qProductRepository.findAll()).thenReturn(Arrays.asList(productData));

        // when
        List<ProductDTO> products = productService.getProductList();

        // then
        assertThat(products).isNotNull().hasSize(1);
    }


    @Test
    public void givenEmptyProductList_whenGetProductList_thenReturnEmptyProductList() {

        // given
        Mockito.when(qProductRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<ProductDTO> products = productService.getProductList();

        // then
        assertThat(products).isNotNull().isEmpty();
    }

    @Test
    public void givenProductId_whenGetProductDetail_thenReturnProductObject() {

        // given
        Long productId = 1L;
        Mockito.when(qProductRepository.findById(productId)).thenReturn(productData);

        // when
        ProductDTO product = productService.getProductDetail(productId);
        System.out.println("product: " + product);

        // then
        assertThat(product).isNotNull();
    }

    @Test
    public void givenProductId_whenGetProductDetail_thenThrowNotFoundException() {

        // given
        Long productId = 1L;
        Mockito.when(qProductRepository.findById(productId)).thenReturn(null);

        // when
        assertThrows(NotFoundException.class, () -> {
            productService.getProductDetail(productId);
        });

        // then
        Mockito.verify(qProductRepository, atLeastOnce()).findById(1L);
    }

    @Test
    public void givenProductObject_whenCreateProduct_thenReturnProductObject() {

        // given
        Mockito.when(qProductRepository.create(productData)).thenReturn(1L);

        // when
        Long productId = productService.createProduct(productDTOData);

        // then
        assertThat(productId).isNotNull();
    }

    @Test
    public void givenProductObject_whenUpdateProduct_thenReturnProductObject() {

        // given
        Mockito.doNothing().when(qProductRepository).update(productData);
        productData.setName("update");

        // when
        productService.updateProduct(productDTOData);

        // then
        assertThat(productDTOData).isNotNull();
        assertThat(productDTOData.getName()).isEqualTo("update");
    }

    @Test
    public void givenNotFoundProductObject_whenUpdateProduct_thenThrowNotFoundException() {

        // given
        Mockito.when(qProductRepository.existsById(productData.getId())).thenReturn(false);
        productData.setName("update");

        // when
        assertThrows(NotFoundException.class, () -> {
            productService.updateProduct(productDTOData);
        });

        // then
        Mockito.verify(qProductRepository, never()).update(any(Product.class));
    }

    @Test
    public void givenProductId_whenDeleteProduct_thenNothing() {

        // given
        Long productId = 1L;
        Mockito.when(qProductRepository.existsById(productId)).thenReturn(true);
        Mockito.doNothing().when(qProductRepository).deleteById(productId);

        // when
        productService.deleteProduct(1L);

        // then
        Mockito.verify(qProductRepository, atLeastOnce()).deleteById(productId);
    }

    @Test
    public void givenNotFoundProductId_whenDeleteProduct_thenThrowNotFound() {

        // given
        Long productId = 1L;
        Mockito.when(qProductRepository.existsById(productId)).thenReturn(false);

        // when
        assertThrows(NotFoundException.class, () -> {
            productService.deleteProduct(1L);
        });

        // then
        Mockito.verify(qProductRepository, never()).deleteById(productId);
    }

}
