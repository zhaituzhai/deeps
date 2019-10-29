package com.zhaojm.deeps.datastructure.list;

public class MyArrayList<E> implements MyList<E> {

    private static final int DEFAULT_SIZE = 10;
    private Object[] data;
    private int size;
    private int index;

    public MyArrayList(){
        this.data = new Object[DEFAULT_SIZE];
        this.size = DEFAULT_SIZE;
    }

    public MyArrayList(int length){
        this.data = new Object[length];
        this.size = length;
    }

    public void add(E e) {
        this.data[index++] = e;
        if(index == size){ // 扩容
            this.size = this.size + (this.size >> 1);
            Object[] newData = new Object[this.size];
            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }
            this.data = newData;
        }
    }

    public void remove(int index) {
        if(index >= 0 && index <= size ) {
            for (int i = index; i < this.data.length - 1; i++) {
                data[i] = data[i + 1];
            }
            this.index--;
        }
    }

    public void remove(Object e) {
        if(!isEmpty()){
            for (int i = 0; i < this.data.length; i++) {
                if(data[i].equals(e)){
                    this.remove(i);
                    break;
                }
            }
        }
    }

    public E get(int i) {
        if(i>=0 && i<=size)
            return (E) this.data[i];
        else
            return null;
    }

    public int size() {
        return this.index;
    }

    public boolean isEmpty() {
        return index <= 0;
    }

    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(5);
        list.add(4);
        System.out.println(list.size());
        System.out.println(list.isEmpty());
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        Integer integer = new Integer(5);
        list.remove(integer);
        System.out.println(list.get(1));
    }
}
