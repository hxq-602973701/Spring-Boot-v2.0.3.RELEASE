package com.example.demo.dal.mapper.main.book;


import com.example.demo.dal.entity.main.book.Book;

import java.util.List;

public interface BookMapper {

    List<Book> selectBooks(Book book);

    void insertBook(Book book);
}