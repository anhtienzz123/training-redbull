package trainingredbull.querydsl;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import trainingredbull.entity.User;

public interface QUserRepository extends CrudRepository<User, Long>, QuerydslPredicateExecutor<User> {

}
