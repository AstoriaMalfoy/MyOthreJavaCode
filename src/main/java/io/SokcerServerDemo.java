package io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author litao34
 * @ClassName SokcerServerDemo
 * @Description TODO
 * @CreateDate 2022/7/22-4:20 PM
 **/
public class SokcerServerDemo {

    private static final int LISTEN_PORT = 9876;

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(LISTEN_PORT);
            ExecutorService executorService = Executors.newFixedThreadPool(20);
            while(true){
                Socket accept = serverSocket.accept();

                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));

                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));

                            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        reader.close();
                                        System.out.println("reader close");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        writer.close();
                                        System.out.println("writer close");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        accept.close();
                                        System.out.println("accept close");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }));

                            String str ;
                            while((str = reader.readLine())!=null){
                                System.out.println("read info" + str);
                                writer.write("receive beat" + '\n');
                                writer.flush();
                            }
                        }catch (Exception e){

                        }
                    }
                });

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
