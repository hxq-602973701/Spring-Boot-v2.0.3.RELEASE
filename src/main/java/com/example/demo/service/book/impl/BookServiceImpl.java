package com.example.demo.service.book.impl;

import com.example.demo.dal.dao.base.BaseDAO;
import com.example.demo.dal.dao.book.BookDAO;
import com.example.demo.dal.entity.main.book.Book;
import com.example.demo.service.base.impl.BaseServiceImpl;
import com.example.demo.service.book.BookService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * BookService
 *
 * @author lt 2018-6-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class BookServiceImpl extends BaseServiceImpl<Book> implements BookService {
    
    /**
     * BookDAO
     */
    @Resource
    private BookDAO bookDAO;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<Book> getDAO() {
        return bookDAO;
    }
}