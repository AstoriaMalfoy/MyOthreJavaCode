package skipList;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
/**
 * @author litao34
 * @ClassName SkipListNode
 * @Description 跳表节点
 * @CreateDate 2022/6/13-2:41 PM
 **/
@Data
@ToString
public class SkipListNode {

    /**
     * 节点值
     */
    private String value;

    /**
     * 节点分数
     */
    private Double score;

    /**
     * 后退指针 父级
     */
    private SkipListNode backWordList;

    /**
     * 层
     */
    private List<ZskipList> zskipListList;

    public SkipListNode(){
        this.value = null;
        this.score = 0d;
        this.backWordList = null;
        this.zskipListList = new ArrayList<>();
        // 初始化头空节点
        for (int  i = 0 ; i < SkipListUtil.ZSKEEPLIST_MAXLEVEL ; i ++){
            zskipListList.add(new ZskipList());
        }
    }

    public SkipListNode getLevelChild(Integer level){
        return this.zskipListList.get(level).getForward();
    }

}

/**
 * 跳跃表层节点
 */
@Data
@ToString
class ZskipList{
    /**
     * 前进指针 子级
     */
    private SkipListNode forward;

    /**
     * 跨度
     */
    private Integer skip;

    public ZskipList(){
        this.forward = null;
        this.skip = 0;
    }
}
