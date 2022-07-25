package io;

import java.io.*;
import java.net.Socket;
import java.util.Date;
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
public class SokcerClientDemo {

    private static String HOST = "127.0.0.1";
    private static Integer PORT = 9876;

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
            ScheduledExecutorService scheduledExecutorService2 = Executors.newSingleThreadScheduledExecutor();

            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        String message;
                        writer.write("beat test" + "-" + uuid + '\n');
                        writer.flush();
                        System.out.println("--> [se] send success " + new Date().toString());
                        while((message = reader.readLine())!=null){
                            System.out.println("<-- [re] receive message {" + message + "}" );
                            break;
                        }
                    } catch (IOException e) {
                    }
                }
            },0,2, TimeUnit.SECONDS);


            while (true){

            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("the main thread stop");
        }
    }
}
