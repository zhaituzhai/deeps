package com.zhaojm.lambda;

import java.util.*;

public class TestStringComparator {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("zhaojm","tom","Aton");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        System.out.println(names);

//        Collections.sort(names, (String o1, String o2) -> {
//            return o1.compareTo(o2);
//        });
        // expression
        // statement
        Collections.sort(names, (o1, o2) -> o1.compareTo(o2));
        System.out.println(names);
    }
}
