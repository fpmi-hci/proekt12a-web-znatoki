package com.bsu.znatoki.service;

import com.bsu.znatoki.dto.CommentDto;
import com.bsu.znatoki.dto.CommentPaginationDto;

import java.util.List;

public interface CommentService {
    CommentDto findCommentById(int id);

    CommentDto save(CommentDto commentDto);

    void deleteById(int id, String editorEmail);

    List<CommentDto> findAllByPaginationDto(CommentPaginationDto paginationDto);
}
