package io.TalkHouseNIO;

import com.sun.tools.internal.xjc.reader.xmlschema.BindPurple;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author litao34
 * @ClassName UserInputHandler
 * @Description TODO
 * @CreateDate 2022/7/25-3:03 PM
 **/
public class UserInputHandler implements Runnable {

    ChatClient chatClient = null;
    public UserInputHandler(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try {
                String s = reader.readLine();
                chatClient.send(s);
                if (s.equals("quit")){
                    break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
