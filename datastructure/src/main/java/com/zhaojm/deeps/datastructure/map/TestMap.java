package com.zhaojm.deeps.datastructure.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestMap {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(String.valueOf(i),i);
        }
        System.out.println(map.get(3));


        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1, "one");
        System.out.println(linkedHashMap.get(1));

        ConcurrentHashMap<Integer, String> conHashMap = new ConcurrentHashMap<>();
        conHashMap.put(2, "two");
        System.out.println(conHashMap.get(2));
    }

}
