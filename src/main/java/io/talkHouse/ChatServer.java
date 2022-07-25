package io.talkHouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author litao34
 * @ClassName ChatServer
 * @Description TODO
 * @CreateDate 2022/7/22-6:12 PM
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatServer {
    private static Integer PORT = 8008;
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private Map<String,ClientInfo> userMap = new ConcurrentHashMap<>();




    public static void main(String[] args) {
        new ChatServer().startServer(new ConcurrentHashMap<>());
    }

    private void startServer(Map<String, ClientInfo> clientInfoMap) {
        System.out.println("start server");
        try {
            ServerSocket serverSocket = new ServerSocket(8008);
            System.out.println("get the sockerServer " + serverSocket.toString() );
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("get socket " + socket.toString());
                executorService.submit(new ChatHandler(userMap,socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
