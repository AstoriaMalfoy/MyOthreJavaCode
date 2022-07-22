package QrLogin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author astoria
 * @ClassName Polling
 * @Description TODO
 * @CreateDate 2021/12/15-5:14 PM
 **/
public class Polling {

    /**
     * 服务端请求获取 QrCodeId
     * @return QrCodeId -- uuid
     */
    public String getQrCodeID(){
        String qrCodeId =  UUID.randomUUID().toString().replace("-","");
        // 设置两分钟超时时间
        SimRedis.putWithTTL(qrCodeId,QrCodeState.QR_STATE_NOSCAN,1000*60*2L);
        return qrCodeId;
    }


    /**
     * 轮询方式查询状态
     * @param qrCodeId
     * @return
     */
    public QrCodeState getQrCodeState(String qrCodeId){
        QrCodeState qrCodeState = (QrCodeState) SimRedis.get(qrCodeId);
        if (Objects.nonNull(qrCodeState)){
            SimRedis.put("access:token:{}".replace("{}",qrCodeId),true);
            return qrCodeState;
        }else{
            return QrCodeState.QR_STATE_TIMEOUT;
        }
    }

    /**
     * 获取token
     * @param qrCodeId
     * @return
     */
    public String getToken(String qrCodeId){
        Boolean access = (Boolean) SimRedis.get("access:token:{}".replace("{}", qrCodeId));
        if (Objects.isNull(access) || !access){
            return "";
        }else{
            return "new token:{}".replace("{}",qrCodeId);
        }

    }

    /**
     * 手机端登录
     * @param qrCodeId
     */
    public void regAccount(String qrCodeId){
        SimRedis.put(qrCodeId,QrCodeState.QR_STATE_FINISH);
        SimRedis.put("access:token:{}".replace("{}",qrCodeId),true);
    }

    /**
     * 手机端扫描
     * @param qrCodeId
     */
    public void scanQrCode(String qrCodeId){
        SimRedis.put(qrCodeId,QrCodeState.QR_STATE_SCANINT);
    }


    public static void main(String[] args) {
        new Polling().getQrCodeID();
    }


    public enum QrCodeState{
        /**
         * 二维码扫描状态信息
         */
        QR_STATE_NOSCAN(1,"未扫描"),
        QR_STATE_SCANINT(2,"扫描中"),
        QR_STATE_FINISH(3,"完成扫描"),
        QR_STATE_TIMEOUT(4,"超时");

        private Integer code;
        private String desc;

        QrCodeState(Integer code, String desc){
            this.code = code;
            this.desc = desc;
        }
    }

}


/**
 * 模拟redis
 */
class SimRedis{
    /**
     * 对象存储
     */
    public static Map<String,Object> objectMap = new HashMap<>();
    /**
     * 更新时间
     */
    public static Map<String,Long> updateTime = new HashMap<>();
    /**
     * 失效时间
     */
    public static Map<String,Long> ttl = new HashMap<>();

    public static Map<String,Long> createTime = new HashMap<>();

    public static void put(String key, Object value){
        objectMap.put(key,value);
        createTime.put(key,System.currentTimeMillis());
        updateTime.put(key,System.currentTimeMillis());
    }

    public static void putWithTTL(String key,Object value,Long ttl){
        SimRedis.ttl.put(key,ttl);
        put(key,value);
    }

    public static Object get(String key){
        if (Objects.nonNull(ttl.get(key))){
            // 未设置超时时间
            return objectMap.get(key);
        }else{
            // 设置了超时时间
            if (System.currentTimeMillis() > ((Long)createTime.get(key)) + ((Long)ttl.get(key))){
                // 超时
                objectMap.remove(key);
                updateTime.remove(key);
                ttl.remove(key);
                createTime.remove(key);
                return null;
            }
            else{
                return objectMap.get(key);
            }
        }
    }


}
