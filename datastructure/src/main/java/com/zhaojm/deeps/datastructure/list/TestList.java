package com.zhaojm.deeps.datastructure.list;

import java.util.*;

public class TestList {

    public static void main(String[] args) {

        System.out.println("========List=============");

        List<String> strList = new ArrayList<>();
        System.out.println(strList.size());
        strList.add("test");   //
        // 把index以后的数组copy 至index + 1 之后，把element 放置index的位置
        strList.add(0, "adada");
        for (Iterator it = strList.iterator(); it.hasNext();){
            String s = (String) it.next();
            System.out.println(s);
        }
//        strList.add(2,"rjf");  // 检查报错
        strList.remove(0);
        strList.clear();

        System.out.println("========Vector=============");

        //  线程同步
        Vector<String> strVector = new Vector<>();
        strVector.add("test");
        strVector.add("test");
        strVector.add(2, "aloha");
        strVector.setElementAt("hello", 1);
        System.out.println(strVector.elementAt(1));

        System.out.println("========Stack=============");

        Stack<String> strStack = new Stack<>();
        strStack.push("hello");
        strStack.push("world");
        System.out.println(strStack.search("hello"));
        System.out.println(strStack.search("234"));
        System.out.println(strStack.peek());
        System.out.println(strStack.pop());
        System.out.println(strStack.peek());

        System.out.println("========Stack=============");

        LinkedList<String> linkedList = new LinkedList(strList);
        linkedList.add("234");
        System.out.println(linkedList);

        System.out.println("========ArrayDeque=============");

        ArrayDeque<String> strArrayDeque = new ArrayDeque<>(43);
        strArrayDeque.addLast("end");
        strArrayDeque.add("hello");
        strArrayDeque.add("loeesest");
        strArrayDeque.addFirst("first");
//        System.out.println(strArrayDeque);
        strArrayDeque.forEach(System.out::println);
        System.out.println(strArrayDeque.pop());
        System.out.println(strArrayDeque);


        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
    }

}
