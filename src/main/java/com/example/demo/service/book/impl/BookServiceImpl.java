package com.example.demo.service.book.impl;

import com.example.demo.dal.dao.base.BaseDAO;
import com.example.demo.dal.dao.book.BookDAO;
import com.example.demo.dal.entity.main.book.Book;
import com.example.demo.service.base.impl.BaseServiceImpl;
import com.example.demo.service.book.BookService;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BookService
 *
 * @author lt 2018-6-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
@Transactional(rollbackFor = Exception.class)
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

    @Cacheable(value = "mysiteforme", key = "#id")
    @Override
    public Integer getById(Long id) {
        Book book = new Book();
        book.setBookId(id);
        book = bookDAO.selectOne(book);
        if (book.getBookId() == 1L) {
            return 365;
        }
        return 365;
    }

    @Cacheable(value = "mysiteforme", key = "#book.bookId")
    @Override
    public List<Book> select11(Book book) {
        System.out.println("使用了缓存了吗？");
        List<Book> books = bookDAO.select(book);
        System.out.println("不使用缓存了");
        return books;
    }
}





