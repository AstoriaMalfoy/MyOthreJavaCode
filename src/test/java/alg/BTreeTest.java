package alg;

import lombok.Getter;
import org.junit.Test;

/**
 * @author litao34
 * @ClassName BTreeTest
 * @Description TODO
 * @CreateDate 2022/5/11-3:31 PM
 **/

public class BTreeTest {

    @Test
    public void initBTreeTest(){

        BTree<Integer,TestEntity> bTree = new BTree();


    }



    @Getter
    class TestEntity implements BTree.BTEntity<Integer>{
        private Integer number;

        private String desc;
        private String uuid;

        @Override
        public Integer getKey() {
            return this.number;
        }
    }
}
