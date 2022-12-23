package com.bsu.znatoki.dto;

import com.bsu.znatoki.entity.enums.OrderSortField;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Builder
@Data
public class OrderPaginationDto {
    private final int skip;
    private final int limit;
    private final Optional<Integer> authorId;
    private final OrderSortField sortField;
    private final Sort.Direction direction;
}
