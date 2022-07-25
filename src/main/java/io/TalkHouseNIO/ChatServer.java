package io.TalkHouseNIO;

import com.mysql.cj.CharsetSettings;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * @author litao34
 * @ClassName ChatServer
 * @Description TODO
 * @CreateDate 2022/7/25-2:32 PM
 **/
@Data
@RequiredArgsConstructor
public class ChatServer {
    /**
     * 缓冲区大小
     */
    public static final int BUFFER = 1024;

    private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(BUFFER);
    private Charset charset = Charset.forName("UTF-8");


    private int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new ChatServer(8080).start();
    }

    private void start(){
        try {
            ServerSocketChannel channel = ServerSocketChannel.open();
            Selector selector = Selector.open();

            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(port));
            channel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("服务启动,监听端口 : " + port);
            while (true){
                if (selector.select() > 0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    for (SelectionKey selectionKey : selectionKeys) {
                        handler(selectionKey,selector);
                    }
                    selectionKeys.clear();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handler(SelectionKey selectionKey, Selector selector) throws IOException {
        if (selectionKey.isAcceptable()){
            ServerSocketChannel client = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socket = client.accept();
            client.configureBlocking(false);

            client.register(selector,SelectionKey.OP_ACCEPT);
            System.out.println("客户端[" + client.socket().getLocalPort() + "]上线了~");
        }
        if (selectionKey.isReadable()){
            SocketChannel client = (SocketChannel) selectionKey.channel();
            String message = receive(client);
            System.out.println("客户端{" + client.socket().getPort()+"}: message " + message);
            sendMessage(client,message,selector);
            if (message.equals("quit")){
                selectionKey.cancel();
                selector.wakeup();
                System.out.println("客户端[" + client.socket().getPort() + "]下线了!");
            }
        }
    }



    private String receive(SocketChannel client) throws IOException {
        readBuffer.clear();
        while (client.read(readBuffer) > 0) {
            readBuffer.flip();
        }
        return String.valueOf(charset.decode(readBuffer));
    }
    private void sendMessage(SocketChannel client, String message, Selector selector) throws IOException {
        message = "客户端[" + client.socket().getPort() + "]:" + message;
        for (SelectionKey key : selector.keys()) {
            if (!(key.channel() instanceof ServerSocketChannel) && !client.equals(key.channel()) && key.isValid()){
                SocketChannel otherClient = (SocketChannel) key.channel();
                writeBuffer.clear();
                writeBuffer.put(charset.encode(message));
                while (writeBuffer.hasRemaining()){
                    otherClient.write(writeBuffer);
                }
            }
        }
    }
}
