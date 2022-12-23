package com.bsu.znatoki.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class CommentDto {
    private int id;
    @NotEmpty
    private String message;

    private int orderId;

    private int authorId;

    private Date createdAt;
}
