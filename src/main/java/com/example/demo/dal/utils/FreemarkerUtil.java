package com.example.demo.dal.utils;


import com.example.demo.dal.entity.main.book.Book;
import com.example.demo.service.book.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Freemarker工具类
 *
 * @author lt 2017-9-1 晚上21:54:52
 * @version 1.0.0
 */

@Component
public class FreemarkerUtil {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

    /**
     * 全局配置
     *
     * @return
     */
    public static Book getBook() {
        BookService bookService = SpringUtil.getBean(BookService.class);
        Book book = new Book();
        book.setBookId(4L);
        return bookService.selectOne(book);
    }
}
