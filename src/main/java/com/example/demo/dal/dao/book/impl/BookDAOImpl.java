package com.example.demo.dal.dao.book.impl;

import com.example.demo.dal.dao.book.BookDAO;
import com.example.demo.dal.entity.main.book.Book;
import com.example.demo.dal.mapper.main.book.BookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * BookDAO
 *
 * @author lt 2017/9/1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class BookDAOImpl  implements BookDAO {

    /**
     * BookMapper
     */
    @Resource
    private BookMapper bookMapper;

    @Override
    public List<Book> selectBooks(Book book) {
        return bookMapper.selectBooks(book);
    }

    @Override
    public void insertBook(Book book) {
        bookMapper.insertBook(book);
    }
}