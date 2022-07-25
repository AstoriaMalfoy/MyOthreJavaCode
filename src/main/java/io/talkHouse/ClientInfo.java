package io.talkHouse;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author litao34
 * @ClassName ClientInfo
 * @Description TODO
 * @CreateDate 2022/7/22-6:13 PM
 **/
@Data
@Builder
public class ClientInfo {
    private String addr;
    private String name;
    private String uuid;
    private Integer port;
    private Socket socket;
}
