package com.bsu.znatoki.service.impl.entitiy;

import com.bsu.znatoki.dto.CommentDto;
import com.bsu.znatoki.dto.CommentPaginationDto;
import com.bsu.znatoki.entity.Comment;
import com.bsu.znatoki.entity.Order;
import com.bsu.znatoki.mapper.CommentMapper;
import com.bsu.znatoki.exception.NotFoundEntityException;
import com.bsu.znatoki.repository.CommentRepository;
import com.bsu.znatoki.service.OrderService;
import com.bsu.znatoki.service.CommentService;
import com.bsu.znatoki.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final OrderService orderService;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto findCommentById(int id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundEntityException(Comment.class, id);
        });
        return commentMapper.mapToDto(comment);
    }

    @Override
    public CommentDto save(CommentDto commentDto) {
        log.info("SAVING COMMENT WITH AUTHOR_ID " + commentDto.getAuthorId());
        if (!orderService.existsById(commentDto.getOrderId())) {
            throw new NotFoundEntityException(Order.class, commentDto.getOrderId());
        }

        Comment newComment = commentMapper.mapToEntity(commentDto);
        newComment.setCreatedAt(new Date());
        commentRepository.save(newComment);
        return commentMapper.mapToDto(newComment);
    }

    @Override
    public void deleteById(int id, String editorEmail) {
        log.info(String.format("DELETING COMMENT WITH ID %d AND EDITOR %S", id, editorEmail));

        int editorId = userService.findIdByEmail(editorEmail);
        int authorId = commentRepository.findAuthorIdByCommentId(id).orElseThrow(() -> {
            throw new NotFoundEntityException(Comment.class, id);
        });
        if (editorId != authorId) {
            throw new AccessDeniedException("This comment doesn't belong to you!");
        }
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> findAllByPaginationDto(CommentPaginationDto paginationDto) {
        log.info("FINDING COMMENTS WITH FILTERS");

        return getComments(Optional.ofNullable(paginationDto.getAuthorId()),
                paginationDto.getOrderId(), paginationDto.getPageable()).stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private List<Comment> getComments(Optional<Integer> authorId, int orderId, Pageable pageable) {
        if (authorId.isEmpty()) {
            return commentRepository.findAllByorderId(orderId);
        }
        return commentRepository.findAllByAuthorIdAndorderId(authorId.get(), orderId, pageable);
    }
}
