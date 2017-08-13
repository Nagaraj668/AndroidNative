package com.basic.androidnativeconcepts.model;

/**
 * Created by NAGARAJ SRJ on 8/13/2017.
 */

public class Book {

    private int bookId;
    private String bookName;

    public Book() {
        super();
    }

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
