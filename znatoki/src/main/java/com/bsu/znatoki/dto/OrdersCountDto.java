package com.bsu.znatoki.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrdersCountDto {
    private final String itemName;
    private final int itemCount;
}
