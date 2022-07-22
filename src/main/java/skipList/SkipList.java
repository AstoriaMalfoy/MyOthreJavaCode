package skipList;

import lombok.Data;
import lombok.ToString;

import java.util.*;

/**
 * @author litao34
 * @ClassName SkipListList
 * @Description TODO
 * @CreateDate 2022/6/13-3:11 PM
 **/
@Data
@ToString
public class SkipList {
    /**
     * 跳跃表头指针
     */
    private SkipListNode head;

    /**
     * 跳跃表尾部指针
     */
    private SkipListNode tail;

    /**
     * 表中节点数量
     */
    private Integer nodeCount;

    /**
     * 表中层数最大的节点层数
     */
    private Integer level;


    /**
     * 初始化节点
     */
    public SkipList(){
        head = new SkipListNode();
        tail = null;
        nodeCount = 0;
        level = 1;
    }

    /**
     * 跳表的节点插入
     * @param score
     * @param value
     * @return
     */
    public Boolean insertNode(Double score,String value){
        // 获取节点层高
        Integer randomLevel = SkipListUtil.getRandomLevel();
        System.out.println("node score=" + score + " value=" + value + " randomLevel=" + randomLevel );
        if (randomLevel > this.level){
            this.level  = randomLevel;
        }
        // 寻找需要插入的节点路径
        Map<Integer,SkipListNode> searchMap = new HashMap<>();
        // 依次寻找各个层级下的插入位置
        Integer maxLevle = Math.max(this.level,randomLevel);
        for (int i = 0 ; i < maxLevle ; i++){
            searchMap.put(i,searchNode(score,value,i));
        }
        // 批量进行插入
        SkipListNode skipListNode = new SkipListNode();
        skipListNode.setScore(score);
        skipListNode.setValue(value);
        searchMap.keySet().stream().forEach(
            key->{
                SkipListNode preNode = searchMap.get(key);
                if (key == 0){
                    skipListNode.setBackWordList(preNode);
                }
                SkipListNode preForward = preNode.getZskipListList().get(key).getForward();
                preNode.getZskipListList().get(key).setForward(skipListNode);
                skipListNode.getZskipListList().get(key).setForward(preForward);
            }
        );
        this.nodeCount ++;
        if (Objects.isNull(tail) || Objects.isNull(skipListNode.getZskipListList().get(0).getForward())){
            this.tail = skipListNode;
        }
        return false;

    }

    private SkipListNode searchNode(Double score, String value,Integer level) {
        SkipListNode currentNode = this.head;
        SkipListNode tempNode = this.head.getLevelChild(level);
        while(Objects.nonNull(currentNode.getLevelChild(level)) && currentNodeisLess(currentNode,score,value)){
            currentNode = currentNode.getLevelChild(level);
        }
        return currentNode;
    }

    private boolean currentNodeisLess(SkipListNode currentNode, Double score, String value) {
        if (currentNode.getScore() < score){
            return true;
        }else if (currentNode.getScore() == score){
            return value.compareTo(currentNode.getValue()) > 0;
        }else{
            return false;
        }
    }

    public void printSkipList(){
        SkipListNode head = this.head;
        for (int  i = 0 ; i  < SkipListUtil.ZSKEEPLIST_MAXLEVEL ; i ++){
            SkipListNode skipListNode = head;
            while (Objects.nonNull(skipListNode)){
                System.out.print("[" + skipListNode.getScore() + " , " + skipListNode.getValue() + "]\t->");
                skipListNode = skipListNode.getZskipListList().get(i).getForward();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        Random random = new Random();
        for (int i = 0 ; i < 10 ; i++ ){
            skipList.insertNode(Double.parseDouble(String.valueOf(random.nextInt() % 100 + 100)),"value" + i );
            skipList.printSkipList();
            System.out.println("------------------------------");
        }

    }

}
