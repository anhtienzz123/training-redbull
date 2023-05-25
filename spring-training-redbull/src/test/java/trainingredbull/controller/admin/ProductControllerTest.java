package trainingredbull.controller.admin;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import trainingredbull.dto.ProductDTO;
import trainingredbull.repository.UserRepository;
import trainingredbull.service.JWTService;
import trainingredbull.service.ProductService;

@WebMvcTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private JWTService jwtService;

    private ProductDTO productDTOData;

    @BeforeEach
    public void setup() {
        productDTOData = ProductDTO.builder().id(1L).name("product dto test").price(100L).build();
    }

    @Test
    public void givenProductList_whenGetProductList_thenReturnProductList() throws Exception {

        // given
        List<ProductDTO> testProducts = Arrays.asList(productDTOData);
        Mockito.when(productService.getProductList()).thenReturn(testProducts);

        // when
        ResultActions response = mockMvc.perform(get("/admin/products"));

        // then
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(testProducts.size())));
    }

    @Test
    public void givenProductObject_whenGetProductDetail_thenReturnProductObject() throws Exception {

        // given
        Mockito.when(productService.getProductDetail(productDTOData.getId()))
                .thenReturn(productDTOData);

        // when
        ResultActions response =
                mockMvc.perform(get("/admin/products/{productId}", productDTOData.getId()));

        // then
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Integer.valueOf(productDTOData.getId() + ""))))
                .andExpect(jsonPath("$.name", is(productDTOData.getName())))
                .andExpect(jsonPath("$.price", is(productDTOData.getPrice())));
    }

    @Test
    public void givenInvalidProductId_whenGetProductById_thenReturnEmpty() throws Exception {
        // given
        Long productId = 1L;
        given(productService.getProductDetail(productId)).willReturn(null);

        // when
        ResultActions response = mockMvc.perform(get("/admin/products/{id}", productId));

        // then - verify the output
        response.andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void givenProductObject_whenCreateProduct_thenReturnProductObject() throws Exception {

        // given
        Mockito.when(productService.createProduct(ArgumentMatchers.any(ProductDTO.class)))
                .thenAnswer(invocation -> {
                    Object tempt = invocation.getArgument(0);
                    return tempt;
                });

        // when
        ResultActions response =
                mockMvc.perform(post("/admin/products").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTOData)));

        // verify
        response.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void givenProductId_whenDeleteProduct_thenReturn200() throws Exception {
        // given
        Long productId = 1L;
        willDoNothing().given(productService).deleteProduct(productId);

        // when
        ResultActions response = mockMvc.perform(delete("/admin/products/{id}", productId));

        // then - verify the output
        response.andExpect(status().isOk()).andDo(print());
    }
    
    @Test
    public void givenProductId_whenDeleteProduct_thenReturnNotFound() throws Exception {
        // given
        Long productId = 0L;
        willDoNothing().given(productService).deleteProduct(productId);

        // when
        ResultActions response = mockMvc.perform(delete("/admin/products/{id}", productId));

        // then - verify the output
        response.andExpect(status().isNotFound()).andDo(print());
    }
}
