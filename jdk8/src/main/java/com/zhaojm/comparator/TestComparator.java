package com.zhaojm.comparator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 比较器
 */
public class TestComparator {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "nihao", "helloworld");

//        Collections.sort(list, (item1, item2) -> item1.length() - item2.length());
//        Collections.sort(list, (item1, item2) -> item2.length() - item1.length());

//        Collections.sort(list, Comparator.comparingInt(String::length).reversed());
        // 无法推断类型  <? super T>  => 比较的参照点可以不同  但是是返回一定是本身类型
        // 类型推断是传入类型输出类型推断的
//        Collections.sort(list, Comparator.comparingInt(item -> item.length()).reversed());
//        Collections.sort(list, Comparator.comparingInt( (String item) -> item.length()).reversed());

//        list.sort(Comparator.comparingInt(String::length));
//        list.sort(Comparator.comparingInt((String item) -> item.length()).reversed());

        // thenComparing 当上一个比较器判断相同才调用下一个比较器，如果上一个已经确定了顺序，则then不工作
        list.sort(Comparator.comparingInt(String::length)
                .thenComparing(String.CASE_INSENSITIVE_ORDER));

        System.out.println(list);

    }

}
