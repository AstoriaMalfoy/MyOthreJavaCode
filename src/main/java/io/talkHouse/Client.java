package io.talkHouse;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author litao34
 * @ClassName Client
 * @Description TODO
 * @CreateDate 2022/7/25-11:33 AM
 **/
public class Client {

    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    ScheduledExecutorService scheduledExecutorService2 = Executors.newSingleThreadScheduledExecutor();


    public static void main(String[] args) {
        new Client().start();
    }

    @SneakyThrows
    public void start(){

        Socket socket = new Socket("127.0.0.1",8008);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        UUID uuid = UUID.randomUUID();

        scheduledExecutorService.scheduleWithFixedDelay(()->{
            Message message = new Message();
            message.setMessage("[" + uuid + "]this is test message " + new Date().toString());
            try {
                bufferedWriter.write(JSON.toJSONString(message) + "\n");
                System.out.println("send message " + JSON.toJSONString(message));
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        },0,1, TimeUnit.SECONDS);


        scheduledExecutorService2.scheduleWithFixedDelay(()->{
            String messageStr;
            try {
                while((messageStr = bufferedReader.readLine())!=null){
                    Message message = JSON.parseObject(messageStr, Message.class);
                    System.out.println("receive message " + messageStr);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        },0,1,TimeUnit.SECONDS);

        while(true){
            Thread.sleep(1000);
        }
    }
}
