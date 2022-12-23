package com.bsu.znatoki.service.impl.entitiy;

import com.bsu.znatoki.dto.OrdersCountDto;
import com.bsu.znatoki.entity.OrderItem;
import com.bsu.znatoki.mapper.ItemCountMapper;
import com.bsu.znatoki.repository.OrderItemRepository;
import com.bsu.znatoki.repository.projections.OrderCountProjection;
import com.bsu.znatoki.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ItemCountMapper itemCountMapper;

    @Override
    public List<OrdersCountDto> findItemsCount() {
        log.info("COUNTING items WITH ITS COUNT");
        List<OrderCountProjection> projections = orderItemRepository.countItems();
        return projections.stream()
                .map(itemCountMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void inititemsIfNotExist(Set<OrderItem> orderItems) {
        if (orderItems != null) {
            Set<OrderItem> newOrderItems = getNewitemsAndInit(orderItems);
            orderItemRepository.saveAll(newOrderItems);
        }
    }

    private Set<OrderItem> getNewitemsAndInit(Set<OrderItem> orderItems) {
        log.info("ADDING NEW items TO DATABASE");

        Map<String, Integer> tagIdByName = itemsListToMap(orderItemRepository.findAll());
        Set<OrderItem> newOrderItems = new HashSet<>();

        orderItems.forEach(tag -> {
            if (tagIdByName.containsKey(tag.getName())) {
                tag.setId(tagIdByName.get(tag.getName()));
            } else {
                newOrderItems.add(tag);
            }
         });
        return newOrderItems;
    }

    private Map<String, Integer> itemsListToMap(List<OrderItem> orderItems) {
        Map<String, Integer> map = new HashMap<>();
        orderItems.forEach(tag -> map.put(tag.getName(), tag.getId()));
        return map;
    }
}
