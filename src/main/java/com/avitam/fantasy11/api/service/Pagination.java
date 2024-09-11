package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.PaginationDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
public interface Pagination {

    default Pageable getPageable(int pageNumber, int pageSize, Sort.Direction direction, String... sort) {
        List<Sort.Order> orders = new ArrayList<>();
        Arrays.stream(sort).collect(Collectors.toList()).forEach(field -> {
            Sort.Order order = new Sort.Order(direction, field);
            orders.add(order);
        });
        return sort != null ? PageRequest.of(pageNumber, pageSize, Sort.by(orders)) : PageRequest.of(pageNumber, pageSize);
    }

    default Pageable getPageable(PaginationDto paginationDto) {
        return PageRequest.of(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());
    }
}
