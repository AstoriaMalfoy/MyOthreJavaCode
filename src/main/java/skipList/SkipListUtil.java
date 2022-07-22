package skipList;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

/**
 * @author litao34
 * @ClassName SkipListUtil
 * @Description 跳表的随机获取层的函数
 * @CreateDate 2022/6/13-2:48 PM
 **/
public class SkipListUtil {

    public  static final Double ZSKEEPLIST_P = 0.25;                // 随机因子 0.25 为啥不是0.5 感觉更应该是0.5

    public static final Integer ZSKEEPLIST_MAXLEVEL = 32;

    /**
     * 获取随机层
     * 有1/(2^K)的概率返回K 时 ZSKEEPLIST_P = 0.5
     * 在redis实现中选择了0.25 可能是为了压缩层高吧
     * @return
     */
    public static Integer getRandomLevel(){
        int level = 1;
        Random random = new Random();
        double ra,rb;
        while((ra = random.nextInt() & 0xFFFF) < (rb = ZSKEEPLIST_P * 0xFFFF)){
            level += 1;
        }
        return level < ZSKEEPLIST_MAXLEVEL ? level : ZSKEEPLIST_MAXLEVEL;
    }


    public static void main(String[] args) {
        Map<Integer,Integer> valueSpan = new HashMap<>();
        for (Integer i = 0 ; i < 1024 ; i ++){
            Integer randomLevel = getRandomLevel();
            if (valueSpan.keySet().contains(randomLevel)){
                Integer value = valueSpan.get(randomLevel);
                valueSpan.put(randomLevel,value+1);
            }else{
                valueSpan.put(randomLevel,1);
            }
        }
        valueSpan.keySet().stream().forEach(
                value->{
                    Integer integer = valueSpan.get(value);
                    System.out.println(value + "    " + integer);
                }
        );
    }
}
