package com.bsu.znatoki.dto;

import com.bsu.znatoki.entity.OrderItem;
import com.bsu.znatoki.entity.enums.OrderStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Data
public class OrderDto {
    private int id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;

    private OrderStatus status;

    private int authorId;

    private Date createdAt;

    private Date updatedAt;

    private Set<OrderItem> orderItems;
}
