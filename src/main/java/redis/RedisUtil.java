package redis;

import org.apache.tools.ant.taskdefs.optional.javah.Kaffeh;
import redis.clients.jedis.Jedis;

import javax.jws.Oneway;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author litao34
 * @ClassName RedisUtil
 * @Description TODO
 * @CreateDate 2022/6/10-2:27 PM
 **/
public class RedisUtil {
    private static final String host = "localhost";

    private static Long HashLock = 1L;
    private static Long NoLock = 0L;

    public static Jedis getRedisClient(){
        return new Jedis(host);
    }

    public static boolean isLock(Object result){
        return HashLock.equals(result);
    }



    /**
     * 上锁
     * @param key
     * @param value
     * @param time
     * @return
     */
    public static boolean lock(String key,String value,String time,Jedis redisClient){
        String script = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1" +
                "then " +
                    "return redis.call('expire',KEYS[1],ARGV[2])" +
                "else " +
                    "return -1" +
                "end";
        return isLock(luaScriptEval(script,Arrays.asList(key),Arrays.asList(value,time),redisClient));
    }

    /**
     * 解锁
     * @param key
     * @param value
     * @param redisClient
     * @return
     */
    public static Integer unLock(String key,String value,Jedis redisClient){
        String script =
                "if redis.call('get',KEYS[1]) == ARGV[1]" +
                "then" +
                        "return redis.call('del',KEYS[1])" +
                "else" +
                        "return -1" +
                "end";
        return Integer.parseInt(String.valueOf(luaScriptEval(script,Arrays.asList(key),Arrays.asList(value),redisClient)));
    }

    /**
     * 尝试解锁 如果一个锁没有设置TTL 那么将该锁删除
     * @param key
     * @param redisCient
     * @return
     */
    public static Integer tryUnLock(String key,Jedis redisCient){
        String script = "" +
                "if redis.call('ttl',KEYS[1]) == -1" +
                "then return redis.call('del',KEYS[1])" +
                "else return -1" +
                "end";
        return Integer.parseInt(String.valueOf(luaScriptEval(script,Arrays.asList(key), Collections.emptyList(),
                redisCient)));

    }

    public static Object luaScriptEval(String script,List keys,List values,Jedis redisClient){
        String scriptHash = redisClient.scriptLoad(script);
        return  redisClient.evalsha(script, keys, values);
    }


    public static void main(String[] args) {
        Jedis jedis = new Jedis(host);
        System.out.println(jedis.ping());
    }
}
