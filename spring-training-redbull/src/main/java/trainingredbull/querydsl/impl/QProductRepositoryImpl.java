package trainingredbull.querydsl.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import trainingredbull.dto.ProductCustomDTO;
import trainingredbull.entity.Product;
import trainingredbull.entity.QProduct;
import trainingredbull.querydsl.QProductRepository;

@Repository
public class QProductRepositoryImpl implements QProductRepository {

    private JPAQueryFactory jpaQueryFactory;

    private final QProduct product = QProduct.product;

    @Autowired
    public QProductRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Product> findAll() {
        return jpaQueryFactory.selectFrom(product).fetch();
    }

    @Override
    public Product findById(Long id) {
        return jpaQueryFactory.selectFrom(product).where(product.id.eq(id)).fetchFirst();
    }

    @Override
    public Long create(Product t) {
        return jpaQueryFactory.insert(product).columns(product.name, product.price)
                .values(t.getName(), t.getPrice()).execute();
    }

    @Override
    public void update(Product t) {
        jpaQueryFactory.update(product).set(product.name, t.getName())
                .set(product.price, t.getPrice()).where(product.id.eq(t.getId())).execute();
    }

    @Override
    public void deleteById(Long id) {
        jpaQueryFactory.delete(product).where(product.id.eq(id)).execute();
    }

    @Override
    public ProductCustomDTO getProductCustomById(Long productId) {
        return jpaQueryFactory
                .select(Projections.constructor(ProductCustomDTO.class, product.id, product.name))
                .from(product).where(product.id.eq(productId)).fetchFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return jpaQueryFactory.selectOne().from(product).where(product.id.eq(id))
                .fetchFirst() != null;
    }
}
