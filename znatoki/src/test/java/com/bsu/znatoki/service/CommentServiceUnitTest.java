package com.bsu.znatoki.service;

import com.bsu.znatoki.entity.Comment;
import com.bsu.znatoki.exception.NotFoundEntityException;
import com.bsu.znatoki.mapper.CommentMapper;
import com.bsu.znatoki.repository.CommentRepository;
import com.bsu.znatoki.service.impl.entitiy.CommentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class CommentServiceUnitTest {
    private final CommentRepository mockCommentRepository = Mockito.mock(CommentRepository.class);
    private final UserService mockUserService = Mockito.mock(UserService.class);
    private final OrderService mockOrderService = Mockito.mock(OrderService.class);
    private final CommentService service = new CommentServiceImpl(mockCommentRepository, mockUserService,
            mockOrderService, new CommentMapper());

    @Test
    public void findCommentById_ShouldReturnExistingComment() {
        Comment existing = getExistingComment();
        when(mockCommentRepository.findById(existing.getId())).thenReturn(Optional.of(existing));
        Assertions.assertEquals(new CommentMapper().mapToDto(existing), service.findCommentById(existing.getId()));
    }

    @Test
    public void findCommentById_ShouldThrowNotExistsExceptionByNonExistingId() {
        Comment nonExisting = getNonExistingComment();
        when(mockCommentRepository.findById(nonExisting.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundEntityException.class, () -> service.findCommentById(nonExisting.getId()));
    }

    private Comment getNonExistingComment() {
        return new Comment(-1, "no such text", -1, -1, new Date());
    }

    private Comment getExistingComment() {
        return new Comment(1, "test comment#1 for post #1", 1, 1, new Date());
    }

}