package com.bsu.znatoki.repository;

import com.bsu.znatoki.entity.OrderItem;
import com.bsu.znatoki.repository.projections.OrderCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

   @Query(value = "SELECT t.name AS itemName, count(t.name) AS itemCount"
           + " FROM  oreds_items ta JOIN  items t ON ta.item_id = t.id"
           + " JOIN  orders a ON ta.orderId =a.id GROUP BY t.name", nativeQuery = true)
   List<OrderCountProjection> countItems();
}
