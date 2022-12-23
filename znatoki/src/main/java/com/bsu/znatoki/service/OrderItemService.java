package com.bsu.znatoki.service;

import com.bsu.znatoki.dto.OrdersCountDto;
import com.bsu.znatoki.entity.OrderItem;

import java.util.List;
import java.util.Set;

public interface OrderItemService {

    List<OrdersCountDto> findItemsCount();

    void inititemsIfNotExist(Set<OrderItem> orderItems);
}
