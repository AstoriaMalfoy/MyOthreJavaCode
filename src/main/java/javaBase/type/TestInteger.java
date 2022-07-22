package javaBase.type;

/**
 * @author litao34
 * @ClassName TestInteger
 * @Description TODO
 * @CreateDate 2022/4/30-8:02 PM
 **/
public class TestInteger {
    private int a = 2147483647;
    private Integer aI  = a;

    public static void main(String[] args) {
        new TestInteger().testA();
    }

    public void testA(){
        System.out.println(a);
        System.out.println(a+1);
        System.out.println(aI);
        System.out.println(aI + 1);
    }
}
