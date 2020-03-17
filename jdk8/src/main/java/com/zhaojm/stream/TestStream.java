package com.zhaojm.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStream {
    public static void main(String[] args) {
        // Stream
        Stream stream1 = Stream.of("hello", "world", "hello world");

        String[] myArrays = new String[]{"hello", "world", "hello world"};
        Stream stream2 = Stream.of(myArrays);
        Stream stream3 = Arrays.stream(myArrays);

        List<String> list = Arrays.asList(myArrays);
        Stream stream4 = list.stream();
        System.out.println("==========");
        //
        IntStream.of(new int[]{3, 4, 5}).forEach(System.out::println);
        IntStream.range(3, 8).forEach(System.out::println);
        System.out.println("=============");

        IntStream.rangeClosed(3, 8).forEach(System.out::println);
        System.out.println("===================");

        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        // 操作   汇聚
        System.out.println(list1.stream().map(i -> 2 * i).reduce(0, Integer::sum));
        System.out.println("=================");

        Stream<String> stream = Stream.of("hello", "world", "hello world");

//        String[] strArray = stream.toArray(len -> new String[len]);
        String[] strArray = stream.toArray(String[]::new);

        System.out.println("============");

        Stream<String> stream5 = Stream.of("hello", "world", "hello world");
//        List<String> strList = stream5.collect(Collectors.toList());
        // 返回一个  ArrayList  -> new ArrayList
        // 把每个元素加入list中  （theList, item） -> theList.add(item)
        // 把最终的结果返回  （theList1, theList2） -> theList1.addAll(theList2)
//        List<String> strList = stream5.collect(() -> new ArrayList(),
//                (theList, item) -> theList.add(item),
//                (theList1, theList2) -> theList1.addAll(theList2));
        List<String> strList = stream5.collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
        System.out.println(strList);

        System.out.println("=============");

        Stream<String> stream6 = Stream.of("hello", "world", "hello world");

//        String strMerger = stream6.collect(() -> new StringBuilder(), (sb, item) -> sb.append(item), (sb1, sb2) -> sb1.append(sb2)).toString();
//        System.out.println(strMerger);

//        System.out.println(stream5.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString());
//        System.out.println(stream6.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString());

        System.out.println(stream6.collect(Collectors.joining()));

        System.out.println("============");
        Stream<String> stream7 = Stream.of("hello", "world", "hello world");
        List<String> collect = stream7.collect(Collectors.toCollection(ArrayList::new));
        collect.forEach(System.out::println);

        System.out.println("===================");

        Stream<String> stream8 = Stream.of("hello", "world", "hello world");
        List<String> strUpper = stream8.map(String::toUpperCase).collect(Collectors.toList());
        strUpper.forEach(System.out::println);

        System.out.println("=======rrr======");

        Stream<List<Integer>> stream9 = Stream.of(Arrays.asList(1), Arrays.asList(2,3,5), Arrays.asList(6,7));
//        stream9.flatMap(theList -> theList.stream()).map(item -> item * item).forEach(System.out::println);
//        stream9.flatMap(Collection::stream).map(item -> item * item).forEach(System.out::println);
        stream9.flatMap(Collection::stream).map(item -> {
            int result = item * item;
            System.out.println(result);
            return result;
        }).collect(Collectors.toList());


        System.out.println("==========================");

        Stream<String> stream10 = Stream.generate(UUID.randomUUID()::toString);
        stream10.findFirst().ifPresent(System.out::println);

        System.out.println("============");

        Stream.iterate(1, item -> item + 2).limit(10).forEach(System.out::println);

        System.out.println("================");
        // 找出流中大于2的元素，然后将每个元素乘2，再忽略前两个元素，再取流前两个，最后求出流中元素的总和
//        Stream.iterate(1, item -> item + 2).limit(6).filter(item -> item > 2)
//                .mapToInt(item -> item * 2).skip(2).limit(2).max().ifPresent(System.out::println);
        IntSummaryStatistics intSummaryStatistics = Stream.iterate(1, item -> item + 2)
                .limit(6)
                .filter(item -> item > 2)
                .mapToInt(item -> item * 2)
                .skip(2)
                .limit(2)
                .summaryStatistics();

        System.out.println(intSummaryStatistics.getMin());
        System.out.println(intSummaryStatistics.getCount());
        System.out.println(intSummaryStatistics.getMax());

        // 注意，流不可重复操作

        Stream<String> stream11 = Stream.of("hello", "world", "hello world");
//        stream11.map(item -> item.substring(0,1).toUpperCase() + item.substring(1))
//                .forEach(System.out::println);

        // 没有终止操作（及早），中间操作才会执行  -> 惰性加载
        stream11.map(item -> {
            String result = item.substring(0,1).toUpperCase() + item.substring(1);
            System.out.println("test");
            return result;
        }).forEach(System.out::println);

    }
}
