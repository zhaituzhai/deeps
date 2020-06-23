package org.mybatis.internal.example.cache;

import org.apache.ibatis.cache.Cache;
import org.mybatis.internal.example.init.RedisClient;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <pre>类名：</pre>
 * <pre>描述：</pre>
 * <pre>版权: 深圳航天信息</pre>
 * <pre>日期: 2020-06-19 10:09</pre>
 *
 * @author zhaojm
 */
public class SelfCache implements Cache {

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String id;

    public SelfCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if(null != value) {
            RedisClient.putValue(key, value);
        }
    }

    @Override
    public Object getObject(Object key) {
        return RedisClient.getValue(key);
    }

    @Override
    public Object removeObject(Object key) {
        return RedisClient.removeValue(key);
    }

    @Override
    public void clear() {
        Set<String> keys = RedisClient.keys("*:" + id + "*");
        if (keys.size() > 0) {
            keys.stream().forEach(RedisClient::removeValue);
        }
    }

    @Override
    public int getSize() {
        return RedisClient.keys("*:" + id + "*").size();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
