package com.bsu.znatoki.controller;

import com.bsu.znatoki.dto.OrderDto;
import com.bsu.znatoki.dto.OrdersCountDto;
import com.bsu.znatoki.service.OrderItemService;
import com.bsu.znatoki.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final OrderItemService orderItemService;
    private final OrderService orderService;

    @GetMapping("/items-cloud")
    public ResponseEntity<List<OrdersCountDto>> getItemCount() {
        return ResponseEntity.ok(orderItemService.findItemsCount());
    }

    @GetMapping("/orders_by")
    public ResponseEntity<List<OrderDto>> getItemsByitems(@RequestParam("items") String[] orderss) {
        return ResponseEntity.ok(orderService.findAllByorderss(orderss));
    }
}
