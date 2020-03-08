package com.zhaojm.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class TestPerson {
    public static void main(String[] args) {
        Person person1 = new Person("zhaojm", 24);
        Person person2 = new Person("tom", 22);
        Person person3 = new Person("Aton", 23);

        List<Person> peoples = Arrays.asList(person1, person2, person3);

        TestPerson testPerson = new TestPerson();
        List<Person> personResult = testPerson.getpersonsByName("zhaojm", peoples);
        personResult.forEach(person -> System.out.println(person.getAge()));

    }

    public List<Person> getpersonsByName(String name, List<Person> persons) {
        return persons.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());
    }

    public List<Person> getPersonsByAge(int age, List<Person> persons){
        BiFunction<Integer, List<Person>, List<Person>> biFunction =
                (ageOfPerson, personList) -> personList.stream().filter(person -> person.getAge() > ageOfPerson).collect(Collectors.toList());
        return biFunction.apply(age, persons);
    }

    public List<Person> getPersonsByAge(int age, List<Person> personList, BiFunction<Integer, List<Person>, List<Person>> biFunction){
        return biFunction.apply(age, personList);
    }

}
