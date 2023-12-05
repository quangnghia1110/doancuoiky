package com.metis.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metis.book.model.Cart;
import com.metis.book.model.user.User;

@Repository
public interface CartReposiroty extends JpaRepository<Cart, Long> {

	Optional<Cart> findById(Long cartId);

}
