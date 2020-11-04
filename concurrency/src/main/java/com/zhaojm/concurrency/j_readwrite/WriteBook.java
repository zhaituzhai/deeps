package com.zhaojm.concurrency.j_readwrite;

public class WriteBook implements Runnable {

    private ReadWriteBook book;
    private String bookName;

    public WriteBook() {
    }

    public WriteBook(ReadWriteBook book, String bookName) {
        this.book = book;
        this.bookName = bookName;
    }


    @Override
    public void run() {
        System.out.println("book.addBook(bookName) = " + book.addBook(bookName));
    }
}
