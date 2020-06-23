package org.mybatis.internal.example.init;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.naming.Name;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Set;

import static com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetSerializer.UTF_8;

/**
 * <pre>类名：</pre>
 * <pre>描述：</pre>
 * <pre>版权: 深圳航天信息</pre>
 * <pre>日期: 2020-06-22 15:48</pre>
 *
 * @author zhaojm
 */
public class RedisClient {

    public static final JedisPool JEDIS_POOL;

    static {
        //redis连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(32);//最大连接数
        config.setMaxIdle(6);//闲置最大连接数
        config.setMinIdle(0);//闲置最小连接数
        config.setMaxWaitMillis(15000);//到达最大连接数后，调用者阻塞时间
        config.setMinEvictableIdleTimeMillis(300000);//连接空闲的最小时间，可能被移除
        config.setSoftMinEvictableIdleTimeMillis(-1);//连接空闲的最小时间，多余最小闲置连接的将被移除
        config.setNumTestsPerEvictionRun(3);//设置每次检查闲置的个数
        config.setTestOnBorrow(false);//申请连接时，是否检查连接有效
        config.setTestOnReturn(false);//返回连接时，是否检查连接有效
        config.setTestWhileIdle(false);//空闲超时,是否执行检查有效
        config.setTimeBetweenEvictionRunsMillis(60000);//空闲检查时间
        config.setBlockWhenExhausted(true);//当连接数耗尽，是否阻塞

        //连接池配置对象+ip+port+timeout+password+dbname
        JEDIS_POOL = new JedisPool(config,"127.0.0.1",6379,15000,null);
    }

    public static String getString(String key) {
        Jedis jedis = JEDIS_POOL.getResource();
        return jedis.get(key);
    }

    public static String putString(String key, String value) {
        Jedis jedis = JEDIS_POOL.getResource();
        return jedis.set(key, value);
    }

    public static String appendString(String key, String value) {
        Jedis jedis = JEDIS_POOL.getResource();
        jedis.append(key, value);
        return jedis.get(key);
    }

    public static <K, T> void putValue(K key, T value) {
        if(null != value) {
            byte[] byteValue = new byte[0];
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);) {
                oos.writeObject(value);
                byteValue = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Jedis jedis = JEDIS_POOL.getResource();
            // TODO key 值使用的 CacheKey 的 toString 键值较长，如何简短并唯一？
            jedis.set(key.toString(), Base64.getEncoder().encodeToString(byteValue));
        }
    }

    public static <K> Object getValue(K key) {
        Jedis jedis = JEDIS_POOL.getResource();
        String valueString = jedis.get(key.toString());
        if(null != valueString && valueString.length() > 0) {
            byte[] byteValue = Base64.getDecoder().decode(valueString);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(byteValue);
                 ObjectInputStream ois = new ObjectInputStream(bais)) {
                return ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <K> Object removeValue(K key) {
        Jedis jedis = JEDIS_POOL.getResource();
        Object value = getValue(key);
        if(null != value) {
            jedis.del(key.toString());
            return value;
        }
        return null;
    }

    public static Set<String> keys(String pattern) {
        Jedis jedis = JEDIS_POOL.getResource();
        return jedis.keys(pattern);
    }

    public static void main(String[] args) {
        System.out.println(getString("name"));
    }

}
