package ByteCode;

import org.junit.Test;

/**
 * @author litao34
 * @ClassName DemoTest1
 * @Description TODO
 * @CreateDate 2022/4/2-5:08 PM
 **/
public class DemoTest1 {

    @Test
    public void testApplication1(){
        int a = 1,b = 10;
        int res1 = add(a,b);
        int res2 = add1(a,b);
        int res3 = addMany(1,10,100,1000,10000,10000,1000000,100000,10000,10000);
    }

    public int add(int a,int b){
        int c = 0;
        c = a + b;
        return c;
    }

    public int add1(int a,int b){
        return a+b;
    }

    public int addMany(int a,int b,int c,int d,int e,int f,int g,int h,int i,int j){
        return a+b+c+d+e+f+g+h+i+j;
    }
}
