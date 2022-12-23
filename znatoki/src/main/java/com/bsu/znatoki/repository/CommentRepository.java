package com.bsu.znatoki.repository;

import com.bsu.znatoki.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByorderId(int orderId);

    @Query("select c.authorId from Comment c where c.id = :commentId")
    Optional<Integer> findAuthorIdByCommentId(@Param("commentId") int commentId);

    List<Comment> findAllByAuthorIdAndorderId(int authorId, int orderId, Pageable pageable);
}
