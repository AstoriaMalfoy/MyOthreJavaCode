package TCF;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

/**
 * @author litao34
 * @ClassName TCF
 * @Description TODO
 * @CreateDate 2022/4/2-3:35 PM
 **/
public class TCF {

    @Test
    public void firstTest(){
        System.out.println(tcf());
    }

    public Node tcf(){
        Node node  = null;
        try {
            node = new Node(11,"this is test node");
            return node;
        }
        finally {
            node = new Node(12,"this is final test node");
        }
    }

    @Data
    @AllArgsConstructor
    public class Node{
        private Integer code;
        private String desc;
    }

}

