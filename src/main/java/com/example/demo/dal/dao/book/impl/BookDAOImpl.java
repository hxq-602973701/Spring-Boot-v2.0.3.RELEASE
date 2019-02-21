package com.example.demo.dal.dao.book.impl;

import com.example.demo.dal.dao.base.impl.BaseDAOImpl;
import com.example.demo.dal.dao.book.BookDAO;
import com.example.demo.dal.entity.main.book.Book;
import com.example.demo.dal.mapper.main.book.BookMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * BookDAO
 *
 * @author lt 2019-2-21
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class BookDAOImpl extends BaseDAOImpl<Book> implements BookDAO {
    
    /**
     * BookMapper
     */
    @Resource
    private BookMapper bookMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<Book> getMapper() {
        return bookMapper;
    }
}