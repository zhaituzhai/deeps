/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * SPI for cache providers.
 * 
 * One instance of cache will be created for each namespace.
 * 
 * The cache implementation must have a constructor that receives the cache id as an String parameter.
 * 
 * MyBatis will pass the namespace as id to the constructor.
 * 
 * <pre>
 * public MyCache(final String id) {
 *  if (id == null) {
 *    throw new IllegalArgumentException("Cache instances require an ID");
 *  }
 *  this.id = id;
 *  initialize();
 * }
 * </pre>
 *
 * mybatis提供了基本实现 {@link org.apache.ibatis.cache.impl.PerpetualCache}，内部采用原始HashMap实现。
 * 第二个需要知道的方面是mybatis有一级缓存和二级缓存。
 *    一级缓存是SqlSession级别的缓存，不同SqlSession之间的缓存数据区域（HashMap）是互相不影响，MyBatis默认支持一级缓存，
 *    不需要任何的配置，默认情况下(一级缓存的有效范围可通过参数localCacheScope参数修改，取值为 SESSION 或者 STATEMENT )，
 *    在一个 SqlSession 的查询期间，只要没有发生 commit/rollback 或者调用 close() 方法，那么 mybatis 就会先根据当前执行语句
 *    的 CacheKey 到一级缓存中查找，如果找到了就直接返回，不到数据库中执行。
 *
 * 对于一级缓存，commit/rollback都会清空一级缓存。
 * 对于二级缓存，DML操作或者显示设置语句层面的flushCache属性都会使得二级缓存失效。
 * 　　在二级缓存容器的具体回收策略实现上，有下列几种：
 *      LRU – 最近最少使用的：移除最长时间不被使用的对象，也是默认的选项，其实现类是{@link org.apache.ibatis.cache.decorators.LruCache}。
 *      FIFO – 先进先出：按对象进入缓存的顺序来移除它们，其实现类是{@link org.apache.ibatis.cache.decorators.FifoCache}。
 *      SOFT – 软引用：移除基于垃圾回收器状态和软引用规则的对象，其实现类是{@link org.apache.ibatis.cache.decorators.SoftCache}。
 *      WEAK – 弱引用：更积极地移除基于垃圾收集器状态和弱引用规则的对象，其实现类是{@link org.apache.ibatis.cache.decorators.WeakCache}。
 * 　　   在缓存的设计上，Mybatis的所有Cache算法都是基于装饰器/Composite模式对PerpetualCache扩展增加功能。
 *
 * @author Clinton Begin
 */

public interface Cache {

  /**
   * @return The identifier of this cache
   */
  String getId();

  /**
   * @param key Can be any object but usually it is a {@link CacheKey}
   * @param value The result of a select.
   */
  void putObject(Object key, Object value);

  /**
   * @param key The key
   * @return The object stored in the cache.
   */
  Object getObject(Object key);

  /**
   * As of 3.3.0 this method is only called during a rollback 
   * for any previous value that was missing in the cache.
   * This lets any blocking cache to release the lock that 
   * may have previously put on the key.
   * A blocking cache puts a lock when a value is null 
   * and releases it when the value is back again.
   * This way other threads will wait for the value to be 
   * available instead of hitting the database.
   *
   * 
   * @param key The key
   * @return Not used
   */
  Object removeObject(Object key);

  /**
   * Clears this cache instance
   */  
  void clear();

  /**
   * Optional. This method is not called by the core.
   * 
   * @return The number of elements stored in the cache (not its capacity).
   */
  int getSize();
  
  /** 
   * Optional. As of 3.2.6 this method is no longer called by the core.
   *  
   * Any locking needed by the cache must be provided internally by the cache provider.
   * 
   * @return A ReadWriteLock 
   */
  ReadWriteLock getReadWriteLock();

}