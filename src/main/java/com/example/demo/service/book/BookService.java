package com.example.demo.service.book;


import com.example.demo.dal.entity.main.book.Book;

import java.util.List;

/**
 * BookService
 *
 * @author lt 2017/9/1
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface BookService {

    List<Book> selectBooks(Book book);

    void insertBook(Book book);
}