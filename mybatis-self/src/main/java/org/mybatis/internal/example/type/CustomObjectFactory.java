package org.mybatis.internal.example.type;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.mybatis.internal.example.pojo.Order;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 要自定义对象工厂类，我们可以实现ObjectFactory这个接口，但是这样我们就需要自己去实现一些在DefaultObjectFactory已经
 * 实现好了的东西，所以也可以继承这个DefaultObjectFactory类，这样可以使得实现起来更为简单。
 * 例如，我们希望给Order对象的属性hostname设置为本地机器名，
 *
 * <objectFactory type="org.mybatis.internal.example.CustomObjectFactory"></objectFactory>
 *
 * @author zhaojm
 * @date 2020-04-01 16:52
 */
public class CustomObjectFactory extends DefaultObjectFactory {

    private static final long serialVersionUID = 1128715667301891724L;

    private static String hostName;
    static {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            // 获取本机ip
            String ip=addr.getHostAddress();
            // 获取本机计算机名称
            hostName = addr.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T create(Class<T> type) {
        T result = super.create(type);
        if(type.equals(Order.class)){
            ((Order)result).setIp(hostName);
        }
        return result;
    }
}
