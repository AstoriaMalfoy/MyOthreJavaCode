import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author litao34
 * @ClassName Unsafe
 * @Description TODO
 * @CreateDate 2022/6/29-2:58 PM
 **/
public class UnsafeTest {

    @SneakyThrows
    public static void main(String[] args) {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe =  (Unsafe)theUnsafe.get(null);
        Student s = (Student)unsafe.allocateInstance(Student.class);
        s.setAge(1);
    }

}

@Data
@AllArgsConstructor
class Student{
    private String name;
    private Integer age;

    public Student(){
        System.out.println("this is init student");
        this.name = "test";
        this.age = 1;
    }
}
