package test;

import io.netty.buffer.ByteBufOutputStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.*;

/**
 * @author litao34
 * @ClassName SerTest
 * @Description TODO
 * @CreateDate 2022/6/30-9:56 AM
 **/
public class SerTest {


    @SneakyThrows
    public static void main(String[] args) {

        Major major = new Major("majorA",10001);
        Student student = new Student("testSt",12,major);

        ByteArrayOutputStream byteArrayOutputStream ;
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream = new ByteArrayOutputStream());
        outputStream.writeObject(student);

        byte[] serializerResult = byteArrayOutputStream.toByteArray();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializerResult);

        ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);

        Student s = (Student)inputStream.readObject();

        System.out.println(student);
        System.out.println(s);

        student.setAge(21);
        student.setName("astoira");
        major.setMajorName("majorB");

        System.out.println(student);
        System.out.println(s);

    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Student implements Serializable {
        private String name;
        private Integer age;
        private Major major;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Major implements Serializable{
        private String majorName;
        private Integer majorId;
    }
}

