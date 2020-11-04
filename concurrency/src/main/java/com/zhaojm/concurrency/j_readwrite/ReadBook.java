package com.zhaojm.concurrency.j_readwrite;

import java.util.ArrayList;
import java.util.List;

public class ReadBook implements Runnable {

    private ReadWriteBook book;
    private int index;

    public ReadBook() {
    }

    public ReadBook(ReadWriteBook book, int index) {
        this.book = book;
        this.index = index;
    }

    @Override
    public void run() {
        System.out.println("book.getBook(index) = " + book.getBook(index));
    }
}
