package com.zhaojm.concurrency.j_readwrite;

import java.util.ArrayList;
import java.util.List;

public class MainRun {

    public static void main(String[] args) {
        List<String> books = new ArrayList<>();
        books.add("爱在黎明破晓前");
        books.add("爱在日落黄昏后");
        books.add("爱在午夜降临前");
        ReadWriteBook book = new ReadWriteBook(books);
        Thread readThread = new Thread(new ReadBook(book, 0));
        Thread read2Thread = new Thread(new ReadBook(book, 1));
        Thread writeThread = new Thread(new WriteBook(book, "爱在你我离别时"));
        Thread write2Thread = new Thread(new WriteBook(book, "爱在程序猿眼中"));
        write2Thread.start();
        writeThread.start();
        readThread.start();
        read2Thread.start();
    }

}
