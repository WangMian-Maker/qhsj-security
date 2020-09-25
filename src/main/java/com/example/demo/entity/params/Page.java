package com.example.demo.entity.params;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private List<T> content;
    private int pageNum;
    private int pageSize;
    private int totalElements;
    private int totalPages;
}
