package io.talkHouse;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author litao34
 * @ClassName ChatHandler
 * @Description TODO
 * @CreateDate 2022/7/25-11:08 AM
 **/
@Data
@AllArgsConstructor
public class ChatHandler implements Runnable{

    private static String USER_KEY = "%s:%s";

    Map<String,ClientInfo> userMap;

    Socket socket;

    @Override
    public void run() {
        try {

            addClient(socket);
            while (socket.isConnected()) {
                List<ClientInfo> brandList = userMap.keySet().stream().map(key -> {
                    return userMap.get(key);
                }).collect(Collectors.toList());
                Message message = getMessage(socket);
                System.out.println("<== getMessageFrom " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + "  message:" + JSON.toJSONString(message));
                brandMessage(brandList, message);
            }
            System.out.println("the socker is closed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void brandMessage(List<ClientInfo> brandList, Message message) {
        brandList.stream().forEach(clientInfo -> {
            try {
                BufferedWriter bufferedWriter =
                        new BufferedWriter(new OutputStreamWriter(clientInfo.getSocket().getOutputStream()));
                String messageStr = JSON.toJSONString(message);
                bufferedWriter.write(messageStr+"\n");
                bufferedWriter.flush();
                System.out.println("==> sandForwardTo " + clientInfo.getSocket().getInetAddress().getHostAddress() +
                        ":" + clientInfo.getPort() + " message:" + messageStr);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Message getMessage(Socket socket)  {
        if (Objects.nonNull(socket)){
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String messageStr;
                while ((messageStr = reader.readLine())!=null){
                    Message message = JSON.parseObject(messageStr, Message.class);
                    return message;
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
            }
        }
        return null;
    }

    private void addClient(Socket socket) {
        String address = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        String clientKey = String.format(USER_KEY, address ,port);
        if (!userMap.containsKey(clientKey)){
            ClientInfo clientInfo = ClientInfo.builder().addr(address).port(port).socket(socket).build();
            userMap.put(clientKey,clientInfo);
        }
    }
}
