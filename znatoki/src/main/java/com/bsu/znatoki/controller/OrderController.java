package com.bsu.znatoki.controller;

import com.bsu.znatoki.dto.OrderDto;
import com.bsu.znatoki.entity.enums.Order;
import com.bsu.znatoki.entity.enums.OrderSortField;
import com.bsu.znatoki.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.bsu.znatoki.controller.FilterConstants.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/my")
    public ResponseEntity<List<OrderDto>> getMyOrders(Principal principal) {
        return ResponseEntity.ok(orderService.findByAuthorEmail(principal.getName()));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getFilteredOrders(
            @RequestParam(value = "skip", defaultValue = DEFAULT_SKIP) int skip,
            @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT) int limit,
            @RequestParam(value = "author", required = false) Integer authorId,
            @RequestParam(value = "sort", defaultValue = DEFAULT_SORT) OrderSortField sortField,
            @RequestParam(value = "order", defaultValue = DEFAULT_ORDER) Order order) {
        Pageable pageable = PageRequest.of(skip, limit, Sort.by(
                Sort.Direction.valueOf(order.name().toUpperCase()), sortField.name())
        );
        List<OrderDto> dtos = orderService.findAllByAuthorAndPageable(authorId, pageable);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDto> post(@Valid @RequestBody OrderDto orderDto, Principal principal) {
        return ResponseEntity.ok(orderService.save(orderDto, principal.getName()));
    }

    @PutMapping("/orders/{id}")
    public void upate(@Valid @RequestBody OrderDto orderDto, @PathVariable int id,
                      Principal principal) {

        orderService.updateById(orderDto, id, principal.getName());
    }

    @DeleteMapping("/orders/{id}")
    public void delete(@PathVariable int id, Principal principal) {
        orderService.deleteById(id, principal.getName());
    }
}

