package com.bsu.znatoki.mapper;

import com.bsu.znatoki.dto.OrdersCountDto;
import com.bsu.znatoki.repository.projections.OrderCountProjection;
import org.springframework.stereotype.Component;

@Component
public class ItemCountMapper {
    public OrdersCountDto mapToDto(OrderCountProjection projection) {
        return new OrdersCountDto(projection.getitemName(), projection.getitemCount());
    }
}
