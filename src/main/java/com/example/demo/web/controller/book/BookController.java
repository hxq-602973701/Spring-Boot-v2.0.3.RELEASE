package com.example.demo.web.controller.book;

import com.example.demo.dal.entity.main.book.Book;
import com.example.demo.service.book.BookService;
import com.example.demo.web.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * 跳转到ftl页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String showIndex(Model model) {
        return "index";
    }

    /**
     * 跳转到ftl页面
     *
     * @param model
     * @return
     */

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String showFtl(Model model) {
        model.addAttribute("username", "ftl");
        Book book = new Book();
        book.setBookId(1L);
        List<Book> bookList = bookService.select11(book);
        return "/book/book_list";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testFtl(Model model) {
        model.addAttribute("username", "ftl");
        return "base";
    }

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public String testFtl1(Model model) {
        model.addAttribute("username", "ftl");
        return "base1";
    }


    /**
     * 同样支持jsp
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/world", method = RequestMethod.GET)
    @Cacheable(value = "mysiteforme", key = "book111")
    public String showJsp(Model model) {
        model.addAttribute("username", "jsp");
        Book book = new Book();
        book.setBookId(1L);
        List<Book> bookList = bookService.select(book);
        return "world";
    }

    /**
     * 获取图书列表
     *
     * @param model
     * @param book
     * @param response
     * @throws Exception
     */
    @Cacheable(value = "mysiteforme", key = "book111")
    @RequestMapping(value = "/book_list", method = RequestMethod.GET)
    public String listAllBookListApi(final Model model, Book book, HttpServletResponse response) throws Exception {
        List<Book> bookList = bookService.select(book);
        objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(bookList);
        return json;
    }

    @RequestMapping(value = "/book_list_id", method = RequestMethod.GET)
    public Integer findById(final Long id) {
        System.out.println("cache miss, invoke find by id, id:" + id);
        Integer a = bookService.getById(id);
        if (a == 365) {

            return 362;
        }
        return 362;
    }

    /**
     * 获取图书分页列表
     *
     * @param model
     * @param book
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/book_pageInfo_List", method = RequestMethod.GET)
    public void listPageBookApi(final Model model, Book book, HttpServletResponse response) throws Exception {

        PageInfo<Book> bookList = bookService.selectPage(book);
        objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(bookList);
        ResponseUtil.write(json, response);
//        DataPipe.in(model).response(json);
    }

    /**
     * 编辑图书页面
     *
     * @param model
     * @param book
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/book/show", method = RequestMethod.GET)
    public String showBookView(final Model model, Book book) throws Exception {

        if (book.getBookId() != null) {
            book = bookService.selectOne(book);
        }
        model.addAttribute("book", book);
        return "/book/book_show";
    }

    /**
     * 更新或者添加图书
     *
     * @param model
     * @param bookBaseStr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/book/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public String saveOrUpdateBookApi(final Model model, String bookBaseStr) throws Exception {

        Book book = objectMapper.readValue(bookBaseStr, Book.class);
        if (book.getBookId() != null) {
            bookService.updateByPrimaryKey(book);
        } else {
            bookService.insert(book);
        }
        return "200";
        /* return objectMapper.writeValueAsString(book);*/
    }

    /**
     * 删除图书
     *
     * @param model
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/book/book_del", method = RequestMethod.GET)
    @ResponseBody
    public void delBookApi(final Model model, String ids) throws Exception {
        String[] idAry = ids.split(",");
        for (String id : idAry) {
            Book book = new Book();
            book.setBookId(Long.valueOf(Integer.valueOf(id)));
            bookService.deleteByPrimaryKey(book);
        }
    }

    /**
     * 子页面获取父页面数据
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/book/shareData", method = RequestMethod.GET)
    public String shareDataView(final Model model) throws Exception {
        return "/book/shareData";
    }

}
