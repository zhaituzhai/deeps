package java.lang;

/**
 * @author zhaojm
 * @date 2020/5/10 15:54
 */
public class String {
    static {
        System.out.println("我是自定义的String类");
    }

    /*
        加载类是需要委托给父类加载器，引导类加载器可以加载 java.lang.String() 源码中的 String，
        加载之后的类二进制则不是本类。就无法执行main方法
     */
    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
