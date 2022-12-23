package com.bsu.znatoki.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Builder
@Data
public class CommentPaginationDto {
    private final Integer authorId;
    private final Integer orderId;
    private final Pageable pageable;
}
