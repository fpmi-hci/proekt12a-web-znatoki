package com.bsu.znatoki.controller;

import com.bsu.znatoki.dto.CommentDto;
import com.bsu.znatoki.dto.CommentPaginationDto;
import com.bsu.znatoki.entity.enums.CommentSortField;
import com.bsu.znatoki.entity.enums.Order;
import com.bsu.znatoki.service.CommentService;
import com.bsu.znatoki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/{orderId}/comments")
    public ResponseEntity<CommentDto> postCommentById(@PathVariable int orderId,
                                                      @Valid @RequestBody CommentDto commentDto,
                                                      Principal principal) {
        commentDto.setOrderId(orderId);
        commentDto.setAuthorId(userService.findIdByEmail(principal.getName()));
        return ResponseEntity.ok(commentService.save(commentDto));

    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> findCommentById(@PathVariable int commentId) {
        return ResponseEntity.ok(commentService.findCommentById(commentId));
    }

    @DeleteMapping("/comments/{commentId}")
    public void delete(@PathVariable int commentId, Principal principal) {
        commentService.deleteById(commentId, principal.getName());
    }

    @GetMapping("/{orderId}/comments")
    public ResponseEntity<List<CommentDto>> аштв(
                                @PathVariable int orderId,
                                @RequestParam(value = "skip", defaultValue = FilterConstants.DEFAULT_SKIP) int skip,
                                @RequestParam(value = "limit", defaultValue = FilterConstants.DEFAULT_LIMIT) int limit,
                                @RequestParam(value = "author", required = false) Integer authorId,
                                @RequestParam(value = "sort", defaultValue = FilterConstants.DEFAULT_SORT) CommentSortField sortField,
                                @RequestParam(value = "order", defaultValue = FilterConstants.DEFAULT_ORDER) Order order) {
        Pageable pageable = PageRequest.of(skip, limit, Sort.by(
                Sort.Direction.valueOf(order.name().toUpperCase()
                ), sortField.name()));
        CommentPaginationDto paginationDto = CommentPaginationDto.builder()
                .authorId(authorId)
                .orderId(orderId)
                .pageable(pageable).build();
        return ResponseEntity.ok(commentService.findAllByPaginationDto(paginationDto));
    }
}
