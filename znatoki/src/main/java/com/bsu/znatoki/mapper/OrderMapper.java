package com.bsu.znatoki.mapper;

import com.bsu.znatoki.dto.OrderDto;
import com.bsu.znatoki.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order mapToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setDescription(orderDto.getText());
        order.setTitle(orderDto.getTitle());
        order.setStatus(orderDto.getStatus());
        order.setUpdatedAt(orderDto.getUpdatedAt());
        order.setCreatedAt(orderDto.getCreatedAt());
        order.setAuthorId(orderDto.getAuthorId());
        order.setOrderItems(orderDto.getOrderItems());
        return order;
    }

    public OrderDto mapToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setStatus(order.getStatus());
        orderDto.setAuthorId(order.getAuthorId());
        orderDto.setText(order.getDescription());
        orderDto.setTitle(order.getTitle());
        orderDto.setCreatedAt(order.getCreatedAt());
        orderDto.setUpdatedAt(order.getUpdatedAt());
        orderDto.setOrderItems(order.getOrderItems());
        return orderDto;
    }
}
