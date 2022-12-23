package com.bsu.znatoki.service.impl.entitiy;

import com.bsu.znatoki.dto.OrderDto;
import com.bsu.znatoki.dto.UserDto;
import com.bsu.znatoki.entity.Order;
import com.bsu.znatoki.entity.Role;
import com.bsu.znatoki.entity.enums.OrderStatus;
import com.bsu.znatoki.mapper.OrderMapper;
import com.bsu.znatoki.exception.NotFoundEntityException;
import com.bsu.znatoki.repository.OrderRepository;
import com.bsu.znatoki.service.OrderService;
import com.bsu.znatoki.service.OrderItemService;
import com.bsu.znatoki.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final UserService userService;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto save(OrderDto orderDto, String authorEmail) {
        log.info("SAVING ORDER WITH AUTHOR_EMAIL " + authorEmail);

        Order order = orderMapper.mapToEntity(orderDto);
        order.setAuthorId(userService.findIdByEmail(authorEmail));
        order.setCreatedAt(new Date());
        order.setStatus(OrderStatus.PUBLIC);

        orderItemService.inititemsIfNotExist(order.getOrderItems());

        return orderMapper.mapToDto(orderRepository.saveAndFlush(order));
    }

    @Override
    public void updateById(OrderDto orderDto, int id, String editorEmail) {
        log.info(String.format("UPDATING ORDER WITH ID %d AND EDITOR %s", id, editorEmail));

        Order order = orderRepository.findById(id).orElseThrow(()->{
            throw new NotFoundEntityException(Order.class, id);
        });
        throwIfDoesntBelong(editorEmail, order);
        updateOrder(orderDto, order);

        orderItemService.inititemsIfNotExist(orderDto.getOrderItems());

        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> findByAuthorEmail(String email) {
        log.info("FIND orders WITH LAZY FETCHING OF items, AUTHOR EMAIL : " + email);
        return fetchLazyItemsFromList(orderRepository.findByAuthorId(userService.findIdByEmail(email)))
                .stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id, String userEmail) {
        assertOrderExistsAndUserHasAccess(userEmail, id);
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDto> findAllByAuthorAndPageable(Integer authorId, Pageable pageable) {
        if (authorId != null) {
            return fetchLazyItemsFromList(orderRepository.findAllByAuthorId(authorId, pageable)).stream()
                    .map(orderMapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return fetchLazyItemsFromPage(orderRepository.findAll(pageable)).stream()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findAllByorderss(String[] orderss) {
        return orderRepository.findDistinctByItems_NameIn(Set.of(orderss)).stream()
                .distinct()
                .map(orderMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(int id) {
        return orderRepository.existsById(id);
    }

    private void throwIfDoesntBelong(String editorEmail, Order order) {
        UserDto editor = userService.findByEmail(editorEmail);
        if (order.getAuthorId() != editor.getId()) {
            throw new AccessDeniedException("This post doesn't belong to you!");
        }
    }

    private void updateOrder(OrderDto orderDto, Order order) {
        order.setDescription(orderDto.getText());
        order.setTitle(orderDto.getTitle());
        order.setOrderItems(orderDto.getOrderItems());
        order.setUpdatedAt(new Date());
    }

    private List<Order> fetchLazyItemsFromList(List<Order> orders) {
        orders.forEach(order -> order.getOrderItems().size());
        return orders;
    }

    private Page<Order> fetchLazyItemsFromPage(Page<Order> orders) {
        orders.forEach(order -> order.getOrderItems().size());
        return orders;
    }

    private void assertOrderExistsAndUserHasAccess(String email, int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new NotFoundEntityException(Order.class, orderId);
        });
        throwIfNotAdminOrAuthor(email, order);
    }

    private void throwIfNotAdminOrAuthor(String email, Order order) {
        UserDto user = userService.findByEmail(email);
        if (!user.getRoles().contains(Role.ADMIN_ROLE) && order.getAuthorId() != user.getId()) {
            throw new AccessDeniedException("This post doesn't belong to you!");
        }
    }
}
