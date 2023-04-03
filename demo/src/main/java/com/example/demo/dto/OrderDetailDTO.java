package com.example.demo.dto;

import com.example.demo.entity.BookEntity;

public class OrderDetailDTO {
    private Long bookId;

    private Integer count;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
