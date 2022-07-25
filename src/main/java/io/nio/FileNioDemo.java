package io.nio;

import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.time.chrono.MinguoChronology;

/**
 * @author litao34
 * @ClassName FileNioDemo
 * @Description TODO
 * @CreateDate 2022/7/25-6:02 PM
 **/
public class FileNioDemo {


    private static final Integer SIZE = 1024;

    Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args) throws Exception {
        new FileNioDemo().start();
    }

    public void start() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/astoria/IdeaProjects/githubPro/testFile.txt","rw");

        FileChannel channel = randomAccessFile.getChannel();

        ByteBuffer reader_buffer = ByteBuffer.allocate(SIZE);
        ByteBuffer write_buffer = ByteBuffer.allocate(SIZE);

        int byteReader = channel.read(reader_buffer);
        write_buffer.put(charset.encode("this is test file contesnt"));
        write_buffer.flip();

//        channel.write(write_buffer);

        while (byteReader!=-1){
            System.out.println("Read:" + byteReader);
            reader_buffer.flip();
            while (reader_buffer.hasRemaining()){
                System.out.println(charset.decode(reader_buffer));
            }
            reader_buffer.clear();
            byteReader = channel.read(reader_buffer);
        }
        channel.close();

    }

}
