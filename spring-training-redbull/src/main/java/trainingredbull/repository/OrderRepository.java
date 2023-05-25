package trainingredbull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trainingredbull.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
