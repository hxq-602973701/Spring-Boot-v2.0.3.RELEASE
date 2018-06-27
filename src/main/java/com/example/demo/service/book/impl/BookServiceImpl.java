package com.example.demo.service.book.impl;

import com.example.demo.dal.dao.book.BookDAO;


import javax.annotation.Resource;

import com.example.demo.dal.entity.main.book.Book;
import com.example.demo.service.book.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BookService
 *
 * @author lt 2017/9/1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class BookServiceImpl  implements BookService {

    /**
     * BookDAO
     */
    @Resource
    private BookDAO bookDAO;

    @Override
    public List<Book> selectBooks(Book book) {
        return bookDAO.selectBooks(book);
    }

    @Override
    public void insertBook(Book book) {
        bookDAO.insertBook(book);
    }
}