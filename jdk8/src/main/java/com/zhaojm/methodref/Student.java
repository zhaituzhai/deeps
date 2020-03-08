package com.zhaojm.methodref;

public class Student {
    private String name;
    private Integer score;

    public Student(){

    }

    public Student(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    /*

     */
    public static int compareStudentByScore(Student student1, Student student2) {
        return student2.getScore() - student1.getScore();
    }
    public static int compareStudentByName(Student student1, Student student2) {
        return student1.getName().compareToIgnoreCase(student2.getName());
    }
    /*

     */
    public int compareByName(Student student){
        return this.getName().compareToIgnoreCase(student.getName());
    }
}
