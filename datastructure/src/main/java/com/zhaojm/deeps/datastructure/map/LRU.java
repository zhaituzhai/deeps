package com.zhaojm.deeps.datastructure.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU<k, v> extends LinkedHashMap<k, v> implements Map<k, v> {

    public LRU(int initialCapacity, float loadFactor, boolean accessOrder){
        super(initialCapacity, loadFactor, accessOrder);
    }

    @Override
    protected boolean removeEldestEntry(Entry<k, v> eldest) {
        if(size() > 6){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        LRU<Character, Integer> lru = new LRU<>(16, 0.75f, true);
        String s = "abcdefghijklmn";
        for (int i = 0; i < s.length(); i++) {
            lru.put(s.charAt(i), i);
        }
        System.out.println("LRU中key为h的Entry的值为：" + lru.get('h'));
        System.out.println("LRU的大小 ：" + lru.size());
        System.out.println("LRU ：" + lru);
    }

}
