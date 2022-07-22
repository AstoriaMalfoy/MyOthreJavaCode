package test;

/**
 * @author litao34
 * @ClassName ConTest
 * @Description TODO
 * @CreateDate 2022/6/29-2:24 PM
 **/
public class ConTest {
    public static void main(String[] args) {
        System.out.println("the main method start ....");
        ConTest conTest = new ConTest();
        invokeMethod(conTest);
        printStr("this is a test method");
        String str = "this is a test mehto";
    }


    public static void invokeMethod(ConTest conTest){
        String demoName = conTest.getDemoName();
        System.out.println(demoName);
    }

    private String name = getDemoName();
    private Integer desc = 1;
    private static String staName = new ConTest().getDemoName();

    private static String getStatName() {
        System.out.println("invoke get static name");
        return "asad";
    }

    public String getDemoName() {
        System.out.println("invoke get name");
        return "asad";

    }

    public ConTest(){
        System.out.println("this is no args constructor");
    }

    public static void printStr(String str){
        System.out.println(str);
    }



}
