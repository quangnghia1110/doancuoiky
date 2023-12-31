package hcmute.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.order.Order;
import hcmute.model.user.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUser(User user);

	List<Order> findTop3ByUser(User user);

	Page<Order> findAllByUser(User user, Pageable pageable);
}
