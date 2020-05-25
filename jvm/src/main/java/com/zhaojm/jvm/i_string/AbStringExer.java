package com.zhaojm.jvm.i_string;

/**
 * -XX:StringTableSize=1009
 * @author zhaojm
 * @date 2020/5/24 22:13
 */
public class AbStringExer {
    String str = new String("good");
    char[] ch = {'t', 'e', 's', 't'};

    private void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'b';
        str.intern();
    }

    public static void main(String[] args) {
        AbStringExer ex = new AbStringExer();
        ex.change(ex.str, ex.ch);
        System.out.println(ex.str);//good
        System.out.println(ex.ch);//best
    }

}
