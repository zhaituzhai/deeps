package com.zhaojm.methodref;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 方法引用
 * 不同于方法调用   string.test()
 */
public class TestMethodRef {
    public static void main(String[] args) {
        Student student1 = new Student("zhaojm", 56);
        Student student2 = new Student("tom", 90);
        Student student3 = new Student("hihi", 86);
        Student student4 = new Student("atom", 26);

        List<Student> studentList = Arrays.asList(student1, student2, student3, student4);

        // 类名 :: 静态方法名
        studentList.sort(Student::compareStudentByScore);
        studentList.forEach(stu -> System.out.println(stu.getName() + " " + stu.getScore()));

        System.out.println("-----------------------");

        // 引用名（对象名） :: 实例方法名
        StudentComparator studentComparator = new StudentComparator();

        studentList.sort((stu1, stu2) -> studentComparator.compareStudentByScore(stu1, stu2));
        studentList.forEach(stu -> System.out.println(stu.getName() + " " + stu.getScore()));

        System.out.println("-----------------------");
        studentList.sort(studentComparator::compareStudentByScore);
        studentList.forEach(stu -> System.out.println(stu.getName() + " " + stu.getScore()));

        System.out.println("-----------------------");

        // 类名::实例方法名
        studentList.sort(Student::compareByName);
        studentList.forEach(stu -> System.out.println(stu.getName() + " " + stu.getScore()));

        System.out.println("-----------------------");

        List<String> cityList = Arrays.asList("beijing", "shanghai", "hunan", "tianjin");
        Collections.sort(cityList, (c1, c2) -> c1.compareToIgnoreCase(c2));
        System.out.println(cityList);

        Collections.sort(cityList, String::compareToIgnoreCase);

        // 构造方法引用  类名::new
        TestMethodRef testMethodRef = new TestMethodRef();
        System.out.println(testMethodRef.getString(String::new));

        System.out.println(testMethodRef.getString("hello ", String::new));

    }

    public String getString(String str, Function<String , String> function){
        return function.apply(str);
    }

    public String getString(Supplier<String> supplier){
        return supplier.get() + "test";
    }
}
