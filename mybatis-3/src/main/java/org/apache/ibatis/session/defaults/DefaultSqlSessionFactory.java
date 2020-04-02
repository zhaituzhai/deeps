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
package org.apache.ibatis.session.defaults;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Clinton Begin
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

  private final Configuration configuration;

  public DefaultSqlSessionFactory(Configuration configuration) {
    this.configuration = configuration;
  }

  @Override
  public SqlSession openSession() {
    return openSessionFromDataSource(configuration.getDefaultExecutorType(), null, false);
  }

  @Override
  public SqlSession openSession(boolean autoCommit) {
    // 使用默认的执行器类型(默认是SIMPLE)，默认隔离级别，非自动提交 委托给openSessionFromDataSource方法
    return openSessionFromDataSource(configuration.getDefaultExecutorType(), null, autoCommit);
  }

  @Override
  public SqlSession openSession(ExecutorType execType) {
    return openSessionFromDataSource(execType, null, false);
  }

  @Override
  public SqlSession openSession(TransactionIsolationLevel level) {
    return openSessionFromDataSource(configuration.getDefaultExecutorType(), level, false);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
    return openSessionFromDataSource(execType, level, false);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
    return openSessionFromDataSource(execType, null, autoCommit);
  }

  @Override
  public SqlSession openSession(Connection connection) {
    return openSessionFromConnection(configuration.getDefaultExecutorType(), connection);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, Connection connection) {
    return openSessionFromConnection(execType, connection);
  }

  @Override
  public Configuration getConfiguration() {
    return configuration;
  }

  /**
   * 返回的事务传递给了执行器，因为执行器是在事务上下文中执行，所以对于自动提交模式，实际上mybatis不需要去关心。
   * 只有非自动管理模式，mybatis才需要关心事务。对于非自动提交模式，通过sqlSession.commit()或sqlSession.rollback()发起，
   * 在进行提交或者回滚的时候会调用isCommitOrRollbackRequired判断是否应该提交或者回滚事务
   * @param execType
   * @param level
   * @param autoCommit
   * @return
   */
  private SqlSession openSessionFromDataSource(ExecutorType execType, TransactionIsolationLevel level, boolean autoCommit) {
    Transaction tx = null;
    try {
      final Environment environment = configuration.getEnvironment();
      // 获取事务管理器, 支持从数据源或者直接获取
      final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
      // 从数据源创建一个事务, 同样,数据源必须配置, mybatis内置了JNDI、POOLED、UNPOOLED三种类型的数据源,
      // 其中POOLED对应的实现为org.apache.ibatis.datasource.pooled.PooledDataSource,它是mybatis自带实现的
      // 一个同步、线程安全的数据库连接池 一般在生产中,我们会使用 dbcp 或者 druid 连接池
      tx = transactionFactory.newTransaction(environment.getDataSource(), level, autoCommit);
      // 拿到事务后，根据事务和执行器类型创建一个真正的执行器实例。
      final Executor executor = configuration.newExecutor(tx, execType);
      // 拿到执行器之后，new 一个 DefaultSqlSession 并返回，这样一个 SqlSession 就创建了，它从逻辑上代表一个封装了事务特性的连接，
      // 如果在此期间发生异常，则调用关闭事务（因为此时事务底层的连接可能已经持有了，否则会导致连接泄露）。
      return new DefaultSqlSession(configuration, executor, autoCommit);
    } catch (Exception e) {
      closeTransaction(tx); // may have fetched a connection so lets call close()
      throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
    } finally {
      ErrorContext.instance().reset();
    }
  }

  private SqlSession openSessionFromConnection(ExecutorType execType, Connection connection) {
    try {
      boolean autoCommit;
      try {
        autoCommit = connection.getAutoCommit();
      } catch (SQLException e) {
        // Failover to true, as most poor drivers
        // or databases won't support transactions
        autoCommit = true;
      }      
      final Environment environment = configuration.getEnvironment();
      final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
      final Transaction tx = transactionFactory.newTransaction(connection);
      final Executor executor = configuration.newExecutor(tx, execType);
      return new DefaultSqlSession(configuration, executor, autoCommit);
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
    } finally {
      ErrorContext.instance().reset();
    }
  }

  private TransactionFactory getTransactionFactoryFromEnvironment(Environment environment) {
    // 如果没有配置environment或者environment的事务管理器为空,则使用受管的事务管理器
    // 除非什么都没有配置,否则在mybatis-config里面,至少要配置一个environment，此时事务工厂不允许为空
    // 对于jdbc类型的事务管理器,则返回JdbcTransactionFactory,其内部操作mybatis的JdbcTransaction实现(采用了Facade模式)，后者对jdbc连接操作
    if (environment == null || environment.getTransactionFactory() == null) {
      return new ManagedTransactionFactory();
    }
    return environment.getTransactionFactory();
  }

  private void closeTransaction(Transaction tx) {
    if (tx != null) {
      try {
        tx.close();
      } catch (SQLException ignore) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }

}
