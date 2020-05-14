package com.zhaojm.jvm.d_stack;

/**
 * 早期绑定与晚期绑定
 * invoke special
 * invoke virtual
 *
 * @author zhaojm
 * @date 2020/5/13 22:59
 */
public class DaMethodInvokTest {

    public void showAnimal (Animal animal) {
        animal.eat(); // 晚期绑定
    }
    public void showHunt(IHuntAble h) {
        h.hunt(); // 晚期绑定
    }

}
class Animal{
    public void eat(){
        System.out.println("动物进食");
    }
}

interface IHuntAble{
    void hunt();
}

class Dog extends Animal implements IHuntAble {
    @Override
    public void eat() {
        System.out.println("狗吃骨头");
    }

    @Override
    public void hunt() {
        System.out.println("捕食耗子，多管闲事");
    }
}

class Cat extends Animal implements IHuntAble {
    public Cat() {
        super(); // 早期绑定
    }
    public Cat(String name) {
        this();
    }

    @Override
    public void eat() {
        System.out.println("猫吃鱼。");
    }

    @Override
    public void hunt() {
        System.out.println("捕食耗子，天经地义。");
    }
}