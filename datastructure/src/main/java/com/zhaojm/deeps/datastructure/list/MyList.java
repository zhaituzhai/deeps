package com.zhaojm.deeps.datastructure.list;

public interface MyList<E> {
    void add(E e);

    void remove(int i);

    void remove(Object e);

    E get(int i);

    int size();

    boolean isEmpty();
}
