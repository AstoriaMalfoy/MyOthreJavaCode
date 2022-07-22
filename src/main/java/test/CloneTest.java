package test;

import lombok.*;

/**
 * @author litao34
 * @ClassName CloneTest
 * @Description TODO
 * @CreateDate 2022/6/29-8:11 PM
 **/
public class CloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        Major major = new Major("计算机科学与技术",100001);
        Student studentA = new Student("astoria",20,major);
        Student studentB = (Student)studentA.clone();

        System.out.println(studentA);
        System.out.println(studentB);

        major.setMajorName("网络工程");
        major.setMajorNumber(12);
        studentA.setName("MalAstoria");
        studentA.setAge(23);

        System.out.println(studentA);
        System.out.println(studentB);
    }
}




@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
class Student implements Cloneable{
    private String name;
    private Integer age;
    private Major major;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Student student = (Student) super.clone();
        student.major = (Major) major.clone();
        return student;

    }
}

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
class Major implements Cloneable{
    private String majorName;
    private Integer majorNumber;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
