package com.zhaojm.sort.newcode;

import java.util.*;
import java.util.stream.Collectors;

public class HuaWeiTest {

    public static void main(String[] args) {
        kSort();

    }

    public static void playCard() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String[] split = str
                .replaceAll("J", "11")
                .replaceAll("Q", "12")
                .replaceAll("K", "13")
                .replaceAll("A", "14").split(" ");
        List<String> params = Arrays.stream(split).collect(Collectors.toList());
        Map<String, String> map = new HashMap<>();
        map.put("11", "J");
        map.put("12", "Q");
        map.put("13", "K");
        map.put("14", "A");
        if (split.length < 5) {
            System.out.println("No");
        }
        boolean flag = false;
        List<String> ret = new ArrayList<>();
        for (int i = 3; i <= 15; i++ ) {
            if (params.contains("" + i)) {
                ret.add("" + i);
            } else {
                if (ret.size() >= 5) {
                    flag = true;
                    for (String s : ret) {
                        params.remove(s);
                        if (null != map.get(s)) {
                            System.out.print(map.get(s));
                        } else {
                            System.out.print(s);
                        }
                        System.out.print(" ");
                    }
                    System.out.println();
                    i = 2;
                }
                ret = new ArrayList<>();
            }
        }
        if (!flag) {
            System.out.println("N0");
        }

    }

    public static void kSort() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        StringBuffer sb = new StringBuffer();
        boolean[] visi = new boolean[n + 1];
        int t = 1,cnt = n;
        for (int i = 2; i <= n; i++) {
            t = t * i;
        }
        while (cnt >0 && k != 0) {
            t = t / cnt;
            cnt--;
            for (int i = 1; i <= n; i++) {
                if (visi[i]) break;
                if (k- t <= 0) {
                    while (visi[i]) i--;
                    visi[i] = true;
                    sb.append(i);
                    break;
                }
                k = k-t;
            }

        }
        for (int i = 1; i <= n; i++) {
            if (!visi[i]) sb.append(i);
        }
        System.out.println(sb);
    }

    public static void englishPos() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String[] split = str.replaceAll("'|,|\\.", " ").split(" ");
        Arrays.sort(split);
        String preStr = sc.next();
        List<String> outList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            if (split[i].startsWith(preStr) && !outList.contains(split[i])){
                outList.add(split[i]);
            }
        }
        if (outList.size() <= 0) {
            System.out.println(preStr);
        } else {
            for (int i = 0; i < outList.size(); i++) {
                System.out.print(outList.get(i));
                System.out.print(" ");
            }
        }
    }

}
