package com.example.demo.service.book;

import com.example.demo.dal.datasource.DataSourceEnum;
import com.example.demo.dal.datasource.DataSourceType;
import com.example.demo.dal.entity.main.book.Book;
import com.example.demo.service.base.BaseService;

import java.util.List;

/**
 * BookService
 *
 * @author lt 2018-6-28
 * @version 1.0.0
 * @category 南阳理工学院
 */
public interface BookService extends BaseService<Book> {
    Integer getById(Long id);

    List<Book> select11(Book book);
}