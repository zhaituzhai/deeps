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
package org.apache.ibatis.executor;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

/**
 * 什么是执行器？
 * 所有我们在应用层通过sqlSession执行的各类selectXXX和增删改操作在做了动态sql和参数相关的封装处理后，都被委托给具体的
 * 执行器去执行，包括一、二级缓存的管理，事务的具体管理，Statement和具体JDBC层面优化的实现等等。
 *
 * 所以执行器比较像是sqlSession下的各个策略工厂实现，用户通过配置决定使用哪个策略工厂。
 * 只不过执行器在一个mybatis配置下只有一个，这可能无法适应于所有的情况，尤其是哪些微服务做得不是特别好的中小型公司，因为
 * 这些系统通常混搭了OLTP和ETL功能。
 *
 * mybatis提供了两种类型的执行器，缓存执行器与非缓存执行器
 * （使用哪个执行器是通过配置文件中settings下的属性defaultExecutorType控制的，默认是SIMPLE），是否使用缓存执行器则
 * 是通过执行cacheEnabled控制的，默认是true。
 *
 * 缓存执行器不是真正功能上独立的执行器，而是非缓存执行器的装饰器模式。
 *
 * 非缓存执行器又分为三种，这三种类型的执行器都基于基础执行器BaseExecutor，基础执行器完成了大部分的公共功能
 *
 * @author Clinton Begin
 */
public interface Executor {

  ResultHandler NO_RESULT_HANDLER = null;

  int update(MappedStatement ms, Object parameter) throws SQLException;

  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;

  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;

  <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;

  List<BatchResult> flushStatements() throws SQLException;

  void commit(boolean required) throws SQLException;

  void rollback(boolean required) throws SQLException;

  CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);

  boolean isCached(MappedStatement ms, CacheKey key);

  void clearLocalCache();

  void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);

  Transaction getTransaction();

  void close(boolean forceRollback);

  boolean isClosed();

  void setExecutorWrapper(Executor executor);

}
