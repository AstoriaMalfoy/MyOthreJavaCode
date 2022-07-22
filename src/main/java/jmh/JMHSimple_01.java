package jmh;

/**
 * @author litao34
 * @ClassName JMHSimple_01
 * @Description TODO
 * @CreateDate 2021/12/27-2:27 PM
 **/
public class JMHSimple_01 {
    public static String demoStr ;
    public static String demoStr2;

    private Integer demoField = 10000;
    private String stringFiels = demoStr + demoStr2;

    public JMHSimple_01(Integer demoField,String str){
        this.demoField = demoField;
        this.stringFiels = str;
    }
    public JMHSimple_01(){

    }

    static {
        demoStr = "this is test demo str";
        demoStr2 = "this is test" + " demo str";
    }

    public static void main(String[] args) {
        JMHSimple_01 jmhSimple_01 = new JMHSimple_01();
        System.out.println(jmhSimple_01);
    }

    public static void TestMethod(String[] str,Integer testInteger,int testint){
        System.out.println(str);
        System.out.println(testint);
        System.out.println(testInteger);
    }

    public static void testBasetype(int integer,float f,double d,char c){
        System.out.println(integer + f +  d + c);
    }

    public static int testResturnInteger(){
        return 1;
    }
}
