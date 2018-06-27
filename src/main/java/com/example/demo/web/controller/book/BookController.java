package com.example.demo.web.controller.book;

import com.example.demo.dal.entity.main.book.Book;
import com.example.demo.service.book.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 一个图书的增删改查
 */
@Controller
public class BookController {

    /**
     * 图书Service
     */
    @Resource
    private BookService bookService;

    /**
     * 最快的json解析器
     */
    private static ObjectMapper objectMapper;


    /**
     * 获取图书列表
     *
     * @param model
     * @param book
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/book_list", method = RequestMethod.GET)
    public void listAllBookListApi(final Model model, Book book, HttpServletResponse response) throws Exception {
        List<Book> bookList = bookService.selectBooks(book);
        objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(bookList);

        bookService.insertBook(book);
    }

    /**
     * 获取图书分页列表
     *
     * @param model
     * @param book
     * @param response
     * @throws Exception
     */
//    @RequestMapping(value = "/book_pageInfo_List", method = RequestMethod.GET)
//    public void listPageBookApi(final Model model, Book book, HttpServletResponse response) throws Exception {
//
//        PageInfo<Book> bookList = bookService.selectPage(book);
//        objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(bookList);
//
////        DataPipe.in(model).response(json);
//    }
}
