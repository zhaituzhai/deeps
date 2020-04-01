/**
 *    Copyright 2009-2017 the original author or authors.
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
package org.apache.ibatis.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;

/**
 * An actual SQL String got from an {@link SqlSource} after having processed any dynamic content.
 * The SQL may have SQL placeholders "?" and an list (ordered) of an parameter mappings 
 * with the additional information for each parameter (at least the property name of the input object to read 
 * the value from). 
 * </br>
 * Can also have additional parameters that are created by the dynamic language (for loops, bind...).
 *
 * @author Clinton Begin
 */
public class BoundSql {

  /**
   * sql文本
   */
  private final String sql;
  /**
   * 静态参数说明
   */
  private final List<ParameterMapping> parameterMappings;
  /**
   * 运行时参数对象
   */
  private final Object parameterObject;
  /**
   * 额外参数主要是维护一些在加载时无法确定的参数，比如标签中的参数在加载时就无法尽最大努力确定，必须通过运行时
   * 执行{@link org.apache.ibatis.scripting.xmltags.DynamicSqlSource#getBoundSql(Object)} ()}
   * 中的 SqlNode.apply() 才能确定真正要执行的SQL语句，以及额外参数。
   *
   * 比如，对于下列的foreach语句，它的AdditionalParameter内容为：
   * {frch_index_0=0, item=2, frch_index_1=1, _parameter=org.mybatis.internal.example.pojo.UserReq@5ccddd20, index=1, frch_item_1=2, _databaseId=null, frch_item_0=1}
   * 其中_parameter和_databaseId在DynamicContext构造器中硬编码，其他值通过调用ForEachSqlNode.apply()计算得到。
   * 与此相对应，此时SQL语句在应用ForeachSqlNode之后，对参数名也进行重写，
   *  select lfPartyId,author as authors,subject,comments,title,partyName
   *      from LfParty where partyName = #{partyName}
   *         AND partyName like #{partyName}
   *         and lfPartyId in
   *          (
   *         #{__frch_item_0.prop}
   *          ,
   *         #{__frch_item_1}
   *          )
   * 然后通过SqlSourceBuilder.parse()调用ParameterMappingTokenHandler计算出该sql的ParameterMapping列表，最后构造出StaticSqlSource。
   */
  private final Map<String, Object> additionalParameters;
  /**
   * 额外参数的facade模式包装
   */
  private final MetaObject metaParameters;

  public BoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
    this.sql = sql;
    this.parameterMappings = parameterMappings;
    this.parameterObject = parameterObject;
    this.additionalParameters = new HashMap<String, Object>();
    this.metaParameters = configuration.newMetaObject(additionalParameters);
  }

  public String getSql() {
    return sql;
  }

  public List<ParameterMapping> getParameterMappings() {
    return parameterMappings;
  }

  public Object getParameterObject() {
    return parameterObject;
  }

  public boolean hasAdditionalParameter(String name) {
    String paramName = new PropertyTokenizer(name).getName();
    return additionalParameters.containsKey(paramName);
  }

  public void setAdditionalParameter(String name, Object value) {
    metaParameters.setValue(name, value);
  }

  public Object getAdditionalParameter(String name) {
    return metaParameters.getValue(name);
  }
}
