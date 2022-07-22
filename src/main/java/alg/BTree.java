package alg;

import com.mchange.v2.codegen.bean.InnerBeanPropertyBeanGenerator;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 * @author ASTORIa
 * @ClassName BTree
 * @Description B树的实现方式
 * @CreateDate 2022/5/9-9:51 PM
 **/
@Getter
public class BTree<K extends Comparable ,V extends BTree.BTEntity> {

    // 定义B树中元素的类型
    interface BTEntity<K extends Comparable>{
        K getKey();
    }


    @Data
    private class BTNodeTemp{
        protected BTNodeTemp parentNode;
        protected List<K> keyList;
        protected List<BTNodeTemp> childNode;
    }


    /**
     * InnerNode 内部节点
     */
    @Data
    private class InnerNode extends BTNodeTemp{
        protected V value;

        InnerNode(){
            parentNode = null;
            childNode.add(0,null);
        }

    }


    // B树的阶
    private int dimension;
    // 根节点
    private InnerNode _rootNode;
    // 上一次search结果的父节点
    private InnerNode _hotNode;
    // B树的容量
    private Integer size;




    public BTree(){
        init(3);
    }

    public BTree(int dimension) throws Exception {
        if (dimension < 3) throw new Exception("B树的维度不能小于3");
        init(3);
    }


    private void init(int dimension){
        this.dimension = dimension;
        this._rootNode = new InnerNode();

    }

    /**
     * 建搜B树中的节点
     * @param key
     * @return
     */
    public V search(K key){
        return null;
    }









}
