package org.mybatis.internal.example.type;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

/**
 * @author zhaojm
 * @date 2020-04-01 16:32
 */
public class CustomWrapperFactory implements ObjectWrapperFactory {

    public boolean hasWrapperFor(Object object) {
        return object != null && object instanceof Map;
    }

    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new CamelMapWrapper(metaObject, (Map) object);
    }
}
