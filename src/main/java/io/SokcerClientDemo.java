package io;

import org.apache.poi.util.StringUtil;
import org.apache.poi.util.SystemOutLogger;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author litao34
 * @ClassName SokcerDemo
 * @Description TODO
 * @CreateDate 2022/7/22-2:57 PM
 **/
public class SokcerDemo {

    private static String HOST = "127.0.0.1";
    private static Integer PORT = 6789;

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket(HOST,PORT);

            String uuid = String.valueOf(UUID.randomUUID());

            // 输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 输出流
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 用户输入流
            BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));

            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

            scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        writer.write("beat test" + "-" + uuid);
                        writer.flush();
                        System.out.println("--> [se] send success " + new Date().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            },0,2, TimeUnit.SECONDS);

            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        String message = reader.readLine();
                        System.out.println("<-- [re] receive message {" + message + "}" );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            },0,2,TimeUnit.SECONDS);


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
