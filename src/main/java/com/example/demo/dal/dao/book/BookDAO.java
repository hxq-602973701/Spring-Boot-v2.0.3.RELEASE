package com.example.demo.dal.dao.book;


import com.example.demo.dal.entity.main.book.Book;

import java.util.List;

/**
 * BookDAO
 *
 * @author lt 2017/9/1
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface BookDAO {

    List<Book> selectBooks(Book book);

    void insertBook(Book book);
}