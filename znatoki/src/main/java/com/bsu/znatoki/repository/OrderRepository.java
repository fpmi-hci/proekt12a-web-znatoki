package com.bsu.znatoki.repository;

import com.bsu.znatoki.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByAuthorId(int authorId);

    List<Order> findAllByAuthorId(int authorId, Pageable pageable);

    List<Order> findDistinctByItems_NameIn(Set<String> names);
}
