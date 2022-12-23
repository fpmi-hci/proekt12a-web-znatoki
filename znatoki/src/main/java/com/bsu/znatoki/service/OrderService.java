package com.bsu.znatoki.service;

import com.bsu.znatoki.dto.OrderDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderDto save(OrderDto orderDto, String authorEmail);

    void updateById(OrderDto orderDto, int id, String editorEmail);

    List<OrderDto> findByAuthorEmail(String email);

    void deleteById(int id, String userEmail);

    List<OrderDto> findAllByAuthorAndPageable(Integer authorId, Pageable pageable);

    List<OrderDto> findAllByorderss(String[] orderss);

    boolean existsById(int id);
}
