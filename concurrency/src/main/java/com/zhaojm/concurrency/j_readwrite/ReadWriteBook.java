package com.zhaojm.concurrency.j_readwrite;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteBook {

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock readLock = lock.readLock();
    private static Lock writeLock = lock.writeLock();

    private List<String> books;

    public ReadWriteBook() {
    }

    public ReadWriteBook(List<String> books) {
        this.books = books;
    }

    public String addBook(String bookName) {
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + "获取了写锁 " + System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(3);
            if (!books.contains(bookName)) {
                books.add(bookName);
            }
            return bookName;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            writeLock.unlock();
        }
    }

    public String getBook(int index) {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + "获取了读锁 " + System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(1);
            if (books.size() > index) {
                return books.get(index);
            } else if (books.size() > 0) {
                return books.get(0);
            } else {
                return null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            readLock.unlock();
        }
    }

}
