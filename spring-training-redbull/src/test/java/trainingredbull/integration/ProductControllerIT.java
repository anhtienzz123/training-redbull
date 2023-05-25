package trainingredbull.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import trainingredbull.dto.ProductDTO;
import trainingredbull.entity.Product;
import trainingredbull.repository.ProductRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzIiwiZXhwIjoxNjg0ODEwNTU1fQ.2BBhA9coBePXPu53tLe91LrSbOpMBiQTxw_JFlIW1y4";

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    public void givenListOfProducts_whenGetAllProducts_thenReturnProductsList() throws Exception {
        // given
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().name("product 1").price(100L).build());
        products.add(Product.builder().name("product 2").price(200L).build());
        productRepository.saveAll(products);

        // when
        ResultActions response =
                mockMvc.perform(get("/admin/products").header("Authorization", TOKEN));;

        // then
        response.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.size()", is(products.size())));

    }

    @Test
    public void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception {
        // given
        Product product = Product.builder().name("product 1").price(100L).build();
        productRepository.save(product);

        // when
        ResultActions response = mockMvc.perform(
                get("/admin/products/{productId}", product.getId()).header("Authorization", TOKEN));

        // then
        response.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.id", is(product.getId())))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.price", is(product.getPrice())));
    }

    @Test
    public void givenInvalidProductId_whenGetProductById_thenReturnEmpty() throws Exception {
        // given
        Long productId = 0L;
        Product product = Product.builder().name("product 1").price(100L).build();
        productRepository.save(product);

        // when
        ResultActions response = mockMvc.perform(
                get("/admin/products/{productId}", productId).header("Authorization", TOKEN));

        // then
        response.andExpect(status().isNotFound()).andDo(print());

    }

    @Test
    public void givenProductObject_whenCreateProduct_thenReturnProductId() throws Exception {
        // given
        ProductDTO productDTO = ProductDTO.builder().name("product 1").price(100L).build();

        // when
        ResultActions response = mockMvc.perform(post("/admin/products")
                .header("Authorization", TOKEN).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)));

        // then
        response.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void givenUpdatedProduct_whenUpdateProduct_thenReturnUpdateProductObject()
            throws Exception {
        // given
        Product product = Product.builder().name("product 1").price(100L).build();
        productRepository.save(product);
        product.setName("product name update");

        // when
        ResultActions response = mockMvc.perform(put("/admin/products")
                .header("Authorization", TOKEN).contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        // then - verify the output
        response.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void givenProductId_whenDeleteEmployee_thenReturn200() throws Exception {
        // given
        Product product = Product.builder().name("product 1").price(100L).build();
        productRepository.save(product);

        // when
        ResultActions response = mockMvc.perform(
                delete("/admin/products/{id}", product.getId()).header("Authorization", TOKEN));

        // then
        response.andExpect(status().isOk()).andDo(print());
    }
}
