package com.zhaojm.deeps.datastructure.array;

public class ArrayTest {
    // 数组长度
    private int size;
    // 数据
    private int data[];
    // 当前已存的数据大小
    private int index;

    // 数组初始化
    public ArrayTest(int size){
        this.size = size;
        this.data = new int[size];
        this.index = 0;
    }

    // 数据的打印
    public void print(){
        System.out.println("index:" + index);
        for (int i = 0; i < size; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

    // 插入数据
    public void inset(int loc, int num){
        this.indexCheck(loc);
        if(index++ < size){
            for(int i = size - 1; i > loc; i--){
                data[i] = data[i - 1];
            }
            data[loc] = num;
        }else{ // 扩容
            this.expansion();
            this.inset(loc, num);
        }
    }

    public void add(int num){
        if(index++ < size - 1){
            data[index - 1] = num;
        }else {
            this.expansion();
            data[index - 1] = num;
        }
    }

    private void expansion() {
        int oldSize = size;
        int newSize = size + (size >> 1);
        int[] newData = new int[newSize];
        System.arraycopy(data, 0, newData, 0, oldSize);
        data = newData;
        size = newSize;
    }

    // 删除数据
    public void delete(int loc){
        this.indexCheck(loc);
        for(int i = loc; i < size; i++){
            if(i != size - 1) {
                data[i] = data[i + 1];
            }else {
                data[i] = 0;
            }
        }
        index--;
    }

    // 修改数据
    public void updata(int loc, int num){
        this.indexCheck(loc);
        if(loc <= size){
            data[loc] = num;
        }
    }

    // 查找数据
    public int get(int loc){
        this.indexCheck(loc);
        return data[loc];
    }

    private void indexCheck(int loc){
        if(index < loc)
            throw new RuntimeException("数组越界");
    }

    public static void main(String[] args) {
        ArrayTest arr = new ArrayTest(6);
        arr.add(1);arr.add(2);arr.add(3);arr.add(4);arr.add(5);
        arr.print();
        arr.add(6);
        arr.print();
        arr.add(7);
        arr.print();
        arr.add(8);
        arr.print();
        arr.add(9);
        arr.print();
        arr.add(10);
        arr.print();
        arr.add(11);
        arr.print();
    }
}
