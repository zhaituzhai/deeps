/**
 *    Copyright 2009-2016 the original author or authors.
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
package org.apache.ibatis.executor.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.ResultHandler;

/**
 * 语句处理器 StatementHandler
 * StatementHandler主要包括prepare语句、给语句设置参数、执行语句获取要执行的SQL语句本身。
 * mybatis包含了三种类型的StatementHandler实现：
 * {@link BaseStatementHandler}
 *    {@link SimpleStatementHandler}
 *    {@link PreparedStatementHandler}
 *    {@link CallableStatementHandler}
 * {@link RoutingStatementHandler}
 *
 * 分别用于JDBC对应的PrepareStatement,Statement以及CallableStatement。BaseStatementHandler是这三种类型
 * 语句处理器的抽象父类，封装了一些实现细节比如设置超时时间、结果集每次提取大小等操作
 *
 * @author Clinton Begin
 */
public interface StatementHandler {

  Statement prepare(Connection connection, Integer transactionTimeout)
      throws SQLException;

  void parameterize(Statement statement)
      throws SQLException;

  void batch(Statement statement)
      throws SQLException;

  int update(Statement statement)
      throws SQLException;

  <E> List<E> query(Statement statement, ResultHandler resultHandler)
      throws SQLException;

  <E> Cursor<E> queryCursor(Statement statement)
      throws SQLException;

  BoundSql getBoundSql();

  ParameterHandler getParameterHandler();

}
