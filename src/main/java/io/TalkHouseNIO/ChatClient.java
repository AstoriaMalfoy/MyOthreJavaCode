package io.TalkHouseNIO;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * @author litao34
 * @ClassName ChatClient
 * @Description TODO
 * @CreateDate 2022/7/25-2:57 PM
 **/
public class ChatClient {
    private static final int BUFFER = 1024;

    private ByteBuffer read_buffer = ByteBuffer.allocate(BUFFER);
    private ByteBuffer write_buffer = ByteBuffer.allocate(BUFFER);

    private SocketChannel client;
    private Selector selector;

    private Charset charset = Charset.forName("UTF-8");

    private void start(){
        try {
            client = SocketChannel.open();
            selector = Selector.open();
            client.configureBlocking(false);
            client.connect(new InetSocketAddress("127.0.0.1",8080));
            while (true){
                int select = selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    handle(selectionKey);
                }
                selectionKeys.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handle(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isConnectable()){
            SocketChannel client = (SocketChannel) selectionKey.channel();
            if (client.finishConnect()){
                new Thread(new UserInputHandler(this)).start();
            }
            client.register(selector,SelectionKey.OP_ACCEPT);
        }
        if (selectionKey.isReadable()){
            SocketChannel client = (SocketChannel) selectionKey.channel();
            String message = receive(client);
            if ("quit".equals(message)){
                selectionKey.cancel();
                selector.wakeup();
            }
        }
    }

    private String receive(SocketChannel client) throws IOException {
        read_buffer.clear();
        while(client.read(read_buffer) > 0){
            read_buffer.flip();
        }
        return String.valueOf(charset.decode(read_buffer));
    }

    public void send(String message) throws Exception{
        if (!message.isEmpty()){
            write_buffer.clear();
            write_buffer.put(charset.encode(message));
            write_buffer.flip();
            while(write_buffer.hasRemaining()){
                client.write(write_buffer);
            }
            if ("quit".equals(message)){
                selector.close();
            }
        }
    }

    public static void main(String[] args) {
        new ChatClient().start();
    }


}
