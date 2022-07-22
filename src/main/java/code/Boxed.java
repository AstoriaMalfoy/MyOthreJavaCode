package code;

import java.util.List;
/**
 * @author litao34
 * @ClassName Boxed
 * @Description TODO
 * @CreateDate 2022/6/28-9:53 AM
 **/
public class Boxed {
public static void main(String[] args) {
    Integer a = 1;
    for(int i =0 ; i < 100000 ; i ++){
        a+=i;
    }
}
}
