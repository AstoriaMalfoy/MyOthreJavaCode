package io.talkHouse;

import com.sun.tools.javac.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author litao34
 * @ClassName Message
 * @Description TODO
 * @CreateDate 2022/7/22-6:32 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private String name;
    private String UUID;
    private List<String> sendUUID;
    private String message;
    private MESSAGE_ACTION message_action;

}


@AllArgsConstructor
enum MESSAGE_ACTION implements Serializable{
    REGISTER(1),
    EXIT(2),
    SEND_MESSAGE(3);

    private Integer messageAction;
}