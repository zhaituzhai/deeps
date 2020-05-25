package com.zhaojm.deeps.datastructure.list;

import java.util.*;

/**
 * @author zhaojm
 * @date 2020/5/24 15:41
 */
public class Main {
    public static ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        //TODO
        ArrayList<Integer> result = new ArrayList<>(k);
        Arrays.sort(input);
        for (int i = 0; i < k; i++) {
            result.add(input[i]);
        }
        return result;
    }



    public static void main(String[] args) {
//        int[] temp = new int[]{10,9,8,7,6,5,4,1};
//        System.out.println(GetLeastNumbers_Solution(temp, 3));
//        Scanner in = new Scanner(System.in);
//
//        while (in.hasNextLine()) {
//            String line = in.nextLine();
//            int k = Integer.parseInt(in.nextLine());
//            int[] input = spliteLine(line);
//            ArrayList<Integer> res = GetLeastNumbers_Solution(input, k);
//            System.out.println(res);
//        }
            Scanner in = new Scanner(System.in);

            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(sort(line));
            }

//        System.out.println((int) 'A');
//
//        System.out.println(((char) ('a' + 1)));
//
//        Scanner in = new Scanner(System.in);
//
//        while (in.hasNextLine()) {
//            String line = in.nextLine();
//            System.out.println(change(line));
//        }
//        Scanner in = new Scanner(System.in);
//
//        while (in.hasNextLine()) {
//            String[]  val = in.nextLine().split(",");
//            int[] x = new int[val.length];
//            for (int i = 0; i < val.length; i++) {
//                x[i] = Integer.parseInt(val[i]);
//            }
//            System.out.println(maximumGap(x));
//        }


//        Scanner in = new Scanner(System.in);
//
//        while (in.hasNext()) {
//            int val = in.nextInt();
//            System.out.println(Arrays.toString(calBits(val)));
//        }


    }

    private static int[] calBits(int val) {
        int[] result = new int[val+1];
        int k;
        for (int i = 0; i <= val; i++) {
            k = 0;
            int n = i;
            if(i==0){
                result[i] = 0;
            }
            while (n != 0) {
                k++;
                n = n &(n-1) ;
            }
            result[i] = k;

        }

        return result;
    }


    public static int maximumGap(int[] nums) {
        Arrays.sort(nums);
        int max = 0;
        for (int i = 0; i < nums.length-1; i++) {
            if(nums[i+1] - nums[i] > max){
                max = nums[i+1] - nums[i];
            }
        }
        return max;
    }


    private static String change(String line) {
        if(line.length() <= 0 ) {
            return "";
        }
        char[] chars = line.toCharArray();
        StringBuffer sb = new StringBuffer();
        char[] result = new char[chars.length];
        int k = 0;
        for (int i = 0; i < chars.length; i++) {
            if(chars[i] >= 'a' && chars[i] <= 'z') {
                sb.append((char) (chars[i] - 32));
            }
            if(chars[i] >= 'A' && chars[i] <= 'Z') {
                sb.append((char) (chars[i] + 32));
            }
        }
        return sb.toString();
    }

    private static String sort(String line) {
        char[] ch = line.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < ch.length; i++) {
            if(map.containsKey(ch[i])) {
                map.put(ch[i], map.get(ch[i])+1);
            }else{
                map.put(ch[i], 1);
            }
        }
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> {return o2.getValue() - o1.getValue();});
        StringBuilder sb = new StringBuilder();
        list.forEach(t -> {
            for (int i = 0; i < t.getValue(); i++) {
                sb.append(t.getKey());
            }
        });
        return sb.toString();
    }


    private static int[] spliteLine(String line) {
        String[] split = line.split(",");
        int[] temp = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            temp[i] = Integer.parseInt(split[i]);
        }
        return temp;
    }
}
