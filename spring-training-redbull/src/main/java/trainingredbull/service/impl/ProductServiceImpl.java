package trainingredbull.service.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import trainingredbull.dto.ProductDTO;
import trainingredbull.entity.Product;
import trainingredbull.querydsl.QProductRepository;
import trainingredbull.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private QProductRepository qProductRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductDTO> getProductList() {
        return qProductRepository.findAll().stream().map(p -> modelMapper.map(p, ProductDTO.class))
                .toList();
    }

    @Override
    public ProductDTO getProductDetail(Long productId) {
        Product product = qProductRepository.findById(productId);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public Long createProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        return qProductRepository.create(product);
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        qProductRepository.update(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        qProductRepository.deleteById(productId);
    }
}
