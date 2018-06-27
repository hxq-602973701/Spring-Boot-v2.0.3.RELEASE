package com.example.demo.dal.entity.main.book;

import com.example.demo.dal.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_book")
public class Book extends BaseEntity {
    @Id
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return book_id
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * @param bookId
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /**
     * @return book_name
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * @param bookName
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * @return book_author
     */
    public String getBookAuthor() {
        return bookAuthor;
    }

    /**
     * @param bookAuthor
     */
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}