package com.bsu.znatoki.mapper;

import com.bsu.znatoki.dto.CommentDto;
import com.bsu.znatoki.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setAuthorId(commentDto.getAuthorId());
        comment.setMessage(commentDto.getMessage());
        comment.setorderId(commentDto.getOrderId());
        comment.setCreatedAt(commentDto.getCreatedAt());
        comment.setId(commentDto.getId());
        return comment;
    }

    public CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setOrderId(comment.getorderId());
        commentDto.setMessage(comment.getMessage());
        commentDto.setId(comment.getId());
        commentDto.setAuthorId(comment.getAuthorId());
        commentDto.setCreatedAt(comment.getCreatedAt());
        return commentDto;
    }
}
