/**
 *    Copyright 2009-2018 the original author or authors.
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
package org.apache.ibatis.builder.xml;

import org.apache.ibatis.builder.*;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.InputStream;
import java.io.Reader;
import java.util.*;

/**
 * @author Clinton Begin
 */
public class XMLMapperBuilder extends BaseBuilder {

  private final XPathParser parser;
  private final MapperBuilderAssistant builderAssistant;
  private final Map<String, XNode> sqlFragments;
  private final String resource;

  @Deprecated
  public XMLMapperBuilder(Reader reader, Configuration configuration, String resource, Map<String, XNode> sqlFragments, String namespace) {
    this(reader, configuration, resource, sqlFragments);
    this.builderAssistant.setCurrentNamespace(namespace);
  }

  @Deprecated
  public XMLMapperBuilder(Reader reader, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
    this(new XPathParser(reader, true, configuration.getVariables(), new XMLMapperEntityResolver()),
        configuration, resource, sqlFragments);
  }

  public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource, Map<String, XNode> sqlFragments, String namespace) {
    this(inputStream, configuration, resource, sqlFragments);
    this.builderAssistant.setCurrentNamespace(namespace);
  }

  public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
    this(new XPathParser(inputStream, true, configuration.getVariables(), new XMLMapperEntityResolver()),
        configuration, resource, sqlFragments);
  }

  private XMLMapperBuilder(XPathParser parser, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
    super(configuration);
    this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
    this.parser = parser;
    this.sqlFragments = sqlFragments;
    this.resource = resource;
  }

  public void parse() {
    if (!configuration.isResourceLoaded(resource)) {
      // 解析mapper的核心
      configurationElement(parser.evalNode("/mapper"));
      configuration.addLoadedResource(resource);
      bindMapperForNamespace();
    }

    parsePendingResultMaps();
    parsePendingCacheRefs();
    parsePendingStatements();
  }

  public XNode getSqlFragment(String refid) {
    return sqlFragments.get(refid);
  }

  /**
   * 解析mapper的核心
   * 1、解析缓存参照cache-ref。参照缓存顾名思义，就是共用其他缓存的设置。
   * 2、解析缓存cache
   * 3、解析参数映射parameterMap
   * 4、解析结果集映射resultMap
   * @param context
   */
  private void configurationElement(XNode context) {
    try {
      String namespace = context.getStringAttribute("namespace");
      if (namespace == null || namespace.equals("")) {
        throw new BuilderException("Mapper's namespace cannot be empty");
      }
      builderAssistant.setCurrentNamespace(namespace);
      // 1、解析缓存参照cache-ref。参照缓存顾名思义，就是共用其他缓存的设置。
      cacheRefElement(context.evalNode("cache-ref"));
      // 2、解析缓存cache
      cacheElement(context.evalNode("cache"));
      // 3、解析参数映射parameterMap
      parameterMapElement(context.evalNodes("/mapper/parameterMap"));
      // 4、解析结果集映射resultMap
      resultMapElements(context.evalNodes("/mapper/resultMap"));
      // 5、解析sql片段
      sqlElement(context.evalNodes("/mapper/sql"));
      // 6、解析CRUD语句
      buildStatementFromContext(context.evalNodes("select|insert|update|delete"));
    } catch (Exception e) {
      throw new BuilderException("Error parsing Mapper XML. The XML location is '" + resource + "'. Cause: " + e, e);
    }
  }

  /**
   * crud的解析从buildStatementFromContext(context.evalNodes(“select|insert|update|delete”));
   * 语句开始，透过调用调用链，我们可以得知SQL语句的解析主要在XMLStatementBuilder中实现。
   * @param list
   */
  private void buildStatementFromContext(List<XNode> list) {
    if (configuration.getDatabaseId() != null) {
      buildStatementFromContext(list, configuration.getDatabaseId());
    }
    buildStatementFromContext(list, null);
  }

  private void buildStatementFromContext(List<XNode> list, String requiredDatabaseId) {
    for (XNode context : list) {
      final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, builderAssistant, context, requiredDatabaseId);
      try {
        statementParser.parseStatementNode();
      } catch (IncompleteElementException e) {
        configuration.addIncompleteStatement(statementParser);
      }
    }
  }

  private void parsePendingResultMaps() {
    Collection<ResultMapResolver> incompleteResultMaps = configuration.getIncompleteResultMaps();
    synchronized (incompleteResultMaps) {
      Iterator<ResultMapResolver> iter = incompleteResultMaps.iterator();
      while (iter.hasNext()) {
        try {
          iter.next().resolve();
          iter.remove();
        } catch (IncompleteElementException e) {
          // ResultMap is still missing a resource...
        }
      }
    }
  }

  private void parsePendingCacheRefs() {
    Collection<CacheRefResolver> incompleteCacheRefs = configuration.getIncompleteCacheRefs();
    synchronized (incompleteCacheRefs) {
      Iterator<CacheRefResolver> iter = incompleteCacheRefs.iterator();
      while (iter.hasNext()) {
        try {
          iter.next().resolveCacheRef();
          iter.remove();
        } catch (IncompleteElementException e) {
          // Cache ref is still missing a resource...
        }
      }
    }
  }

  private void parsePendingStatements() {
    Collection<XMLStatementBuilder> incompleteStatements = configuration.getIncompleteStatements();
    synchronized (incompleteStatements) {
      Iterator<XMLStatementBuilder> iter = incompleteStatements.iterator();
      while (iter.hasNext()) {
        try {
          iter.next().parseStatementNode();
          iter.remove();
        } catch (IncompleteElementException e) {
          // Statement is still missing a resource...
        }
      }
    }
  }

  /**
   * 1、解析缓存参照cache-ref。参照缓存顾名思义，就是共用其他缓存的设置。
   *
   *    <cache-ref namespace=”com.someone.application.data.SomeMapper”/>
   *
   * 缓存参考因为通过namespace指向其他的缓存。
   * 所以会出现第一次解析的时候指向的缓存还不存在的情况，所以需要在所有的mapper文件加载完成后进行二次处理，不仅仅是缓存参考，
   * 其他的CRUD也一样。所以在XMLMapperBuilder.configuration中有很多的incompleteXXX，这种设计模式类似于JVM GC中的
   * mark and sweep，标记、然后处理。
   *
   * 所以当捕获到IncompleteElementException异常时，没有终止执行，而是将指向的缓存不存在的cacheRefResolver添加到
   * configuration.incompleteCacheRef中。
   * @param context
   */
  private void cacheRefElement(XNode context) {
    if (context != null) {
      configuration.addCacheRef(builderAssistant.getCurrentNamespace(), context.getStringAttribute("namespace"));
      CacheRefResolver cacheRefResolver = new CacheRefResolver(builderAssistant, context.getStringAttribute("namespace"));
      try {
        cacheRefResolver.resolveCacheRef();
      } catch (IncompleteElementException e) {
        configuration.addIncompleteCacheRef(cacheRefResolver);
      }
    }
  }

  /**
   * 默认情况下，mybatis使用的是永久缓存PerpetualCache，读取或设置各个属性默认值之后，调用builderAssistant.useNewCache构建缓存，
   * 其中的CacheBuilder使用了build模式（在effective里面，建议有4个以上可选属性时，应该为对象提供一个builder便于使用），只要实
   * 现org.apache.ibatis.cache.Cache接口，就是合法的mybatis缓存。
   * 我们先来看下缓存的DTD定义：
   *      <!ELEMENT cache (property*)>
   *      <!ATTLIST cache
   *      type CDATA #IMPLIED
   *      eviction CDATA #IMPLIED
   *      flushInterval CDATA #IMPLIED
   *      size CDATA #IMPLIED
   *      readOnly CDATA #IMPLIED
   *      blocking CDATA #IMPLIED >
   * 所以，最简单的情况下只要声明就可以启用当前mapper下的缓存。
   *
   * @param context
   * @throws Exception
   */
  private void cacheElement(XNode context) throws Exception {
    if (context != null) {
      String type = context.getStringAttribute("type", "PERPETUAL");
      Class<? extends Cache> typeClass = typeAliasRegistry.resolveAlias(type);
      String eviction = context.getStringAttribute("eviction", "LRU");
      Class<? extends Cache> evictionClass = typeAliasRegistry.resolveAlias(eviction);
      Long flushInterval = context.getLongAttribute("flushInterval");
      Integer size = context.getIntAttribute("size");
      boolean readWrite = !context.getBooleanAttribute("readOnly", false);
      boolean blocking = context.getBooleanAttribute("blocking", false);
      Properties props = context.getChildrenAsProperties();
      builderAssistant.useNewCache(typeClass, evictionClass, flushInterval, size, readWrite, blocking, props);
    }
  }

  /**
   * 3、解析参数映射parameterMap
   *      <!ELEMENT parameterMap (parameter+)?>
   *      <!ATTLIST parameterMap
   *      id CDATA #REQUIRED
   *      type CDATA #REQUIRED
   *      >
   *
   *      <!ELEMENT parameter EMPTY>
   *      <!ATTLIST parameter
   *      property CDATA #REQUIRED
   *      javaType CDATA #IMPLIED
   *      jdbcType CDATA #IMPLIED
   *      mode (IN | OUT | INOUT) #IMPLIED
   *      resultMap CDATA #IMPLIED
   *      scale CDATA #IMPLIED
   *      typeHandler CDATA #IMPLIED
   *      >
   * 总体来说，目前已经不推荐使用参数映射，而是直接使用内联参数。
   * @param list
   * @throws Exception
   */
  private void parameterMapElement(List<XNode> list) throws Exception {
    for (XNode parameterMapNode : list) {
      String id = parameterMapNode.getStringAttribute("id");
      String type = parameterMapNode.getStringAttribute("type");
      Class<?> parameterClass = resolveClass(type);
      List<XNode> parameterNodes = parameterMapNode.evalNodes("parameter");
      List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
      // 循环获得所有的ParameterMapping集合
      for (XNode parameterNode : parameterNodes) {
        String property = parameterNode.getStringAttribute("property");
        String javaType = parameterNode.getStringAttribute("javaType");
        String jdbcType = parameterNode.getStringAttribute("jdbcType");
        String resultMap = parameterNode.getStringAttribute("resultMap");
        String mode = parameterNode.getStringAttribute("mode");
        String typeHandler = parameterNode.getStringAttribute("typeHandler");
        Integer numericScale = parameterNode.getIntAttribute("numericScale");
        ParameterMode modeEnum = resolveParameterMode(mode);
        Class<?> javaTypeClass = resolveClass(javaType);
        JdbcType jdbcTypeEnum = resolveJdbcType(jdbcType);
        @SuppressWarnings("unchecked")
        Class<? extends TypeHandler<?>> typeHandlerClass = (Class<? extends TypeHandler<?>>) resolveClass(typeHandler);
        ParameterMapping parameterMapping = builderAssistant.buildParameterMapping(parameterClass, property, javaTypeClass, jdbcTypeEnum, resultMap, modeEnum, typeHandlerClass, numericScale);
        parameterMappings.add(parameterMapping);
      }
      // 创建ParameterMap并加入List<ParameterMapping>，同时把ParameterMap注册到Configuration内。
      builderAssistant.addParameterMap(id, parameterClass, parameterMappings);
    }
  }

  /**
   * 4、解析结果集映射resultMap
   * 结果集映射早期版本可以说是用的最多的辅助节点了，不过有了mapUnderscoreToCamelCase属性之后，如果命名规范控制做的好的话，resultMap也是可以省略的。
   * 每个mapper文件可以有多个结果集映射。最终来说，它还是使用频率很高的。
   * 我们先来看下DTD定义： mybatis-3-config.dtd
   *
   * @param list
   * @throws Exception
   */
  private void resultMapElements(List<XNode> list) throws Exception {
    for (XNode resultMapNode : list) {
      try {
        resultMapElement(resultMapNode);
      } catch (IncompleteElementException e) {
        // ignore, it will be retried
      }
    }
  }

  private ResultMap resultMapElement(XNode resultMapNode) throws Exception {
    return resultMapElement(resultMapNode, Collections.<ResultMapping> emptyList());
  }

  private ResultMap resultMapElement(XNode resultMapNode, List<ResultMapping> additionalResultMappings) throws Exception {
    ErrorContext.instance().activity("processing " + resultMapNode.getValueBasedIdentifier());
    String id = resultMapNode.getStringAttribute("id",
        resultMapNode.getValueBasedIdentifier());
    String type = resultMapNode.getStringAttribute("type",
        resultMapNode.getStringAttribute("ofType",
            resultMapNode.getStringAttribute("resultType",
                resultMapNode.getStringAttribute("javaType"))));
    String extend = resultMapNode.getStringAttribute("extends");
    Boolean autoMapping = resultMapNode.getBooleanAttribute("autoMapping");
    Class<?> typeClass = resolveClass(type);
    Discriminator discriminator = null;
    List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
    resultMappings.addAll(additionalResultMappings);
    List<XNode> resultChildren = resultMapNode.getChildren();
    for (XNode resultChild : resultChildren) {
      if ("constructor".equals(resultChild.getName())) {
        // 构造器
        processConstructorElement(resultChild, typeClass, resultMappings);
      } else if ("discriminator".equals(resultChild.getName())) {
        // 鉴别器discriminator的解析
        discriminator = processDiscriminatorElement(resultChild, typeClass, resultMappings);
      } else {
        List<ResultFlag> flags = new ArrayList<ResultFlag>();
        if ("id".equals(resultChild.getName())) {
          flags.add(ResultFlag.ID);
        }
        resultMappings.add(buildResultMappingFromContext(resultChild, typeClass, flags));
      }
    }
    // 最后所有的子节点都被解析到resultMappings中， 在解析完整个resultMap中的所有子元素之后，调用ResultMapResolver进行解析
    ResultMapResolver resultMapResolver = new ResultMapResolver(builderAssistant, id, typeClass, extend, discriminator, resultMappings, autoMapping);
    try {
      return resultMapResolver.resolve();
    } catch (IncompleteElementException  e) {
      configuration.addIncompleteResultMap(resultMapResolver);
      throw e;
    }
  }

  /**
   * 构造器主要用于没有默认构造器或者有多个构造器的情况
   *
   * public class User {
   *    //...
   *    public User(Integer id, String username, int age) {
   *      //...
   *   }
   * //...
   * }
   *
   * <constructor>
   *    <idArg column="id" javaType="int"/>
   *    <arg column="username" javaType="String"/>
   *    <arg column="age" javaType="_int"/>
   * </constructor>
   *
   * @param resultChild
   * @param resultType
   * @param resultMappings
   * @throws Exception
   */
  private void processConstructorElement(XNode resultChild, Class<?> resultType, List<ResultMapping> resultMappings) throws Exception {
    List<XNode> argChildren = resultChild.getChildren();
    for (XNode argChild : argChildren) {
      List<ResultFlag> flags = new ArrayList<ResultFlag>();
      flags.add(ResultFlag.CONSTRUCTOR);
      if ("idArg".equals(argChild.getName())) {
        flags.add(ResultFlag.ID);
      }
      // 最后调用buildResultMappingFromContext建立具体的resultMap。
      resultMappings.add(buildResultMappingFromContext(argChild, resultType, flags));
    }
  }

  /**
   * 鉴别器非常容易理解,它的表现很像Java语言中的switch语句。
   * 定义鉴别器也是通过column和javaType属性来唯一标识，
   *      column是用来确定某个字段是否为鉴别器，
   *      JavaType是需要被用来保证等价测试的合适类型。
   * 例如：
   *
   *  <discriminator javaType="int" column="vehicle_type">
   *     <case value="1" resultMap="carResult"/>
   *     <case value="2" resultMap="truckResult"/>
   *     <case value="3" resultMap="vanResult"/>
   *     <case value="4" resultMap="suvResult"/>
   *   </discriminator>
   *
   * 对于上述的鉴别器，如果 vehicle_type=1, 那就会使用下列这个结果映射。
   * <resultMap id="carResult" type="Car">
   *   <result property="doorCount" column="door_count" />
   * </resultMap>
   *
   * @param context
   * @param resultType
   * @param resultMappings
   * @return
   * @throws Exception
   */
  private Discriminator processDiscriminatorElement(XNode context, Class<?> resultType, List<ResultMapping> resultMappings) throws Exception {
    String column = context.getStringAttribute("column");
    String javaType = context.getStringAttribute("javaType");
    String jdbcType = context.getStringAttribute("jdbcType");
    String typeHandler = context.getStringAttribute("typeHandler");
    Class<?> javaTypeClass = resolveClass(javaType);
    @SuppressWarnings("unchecked")
    Class<? extends TypeHandler<?>> typeHandlerClass = (Class<? extends TypeHandler<?>>) resolveClass(typeHandler);
    JdbcType jdbcTypeEnum = resolveJdbcType(jdbcType);
    Map<String, String> discriminatorMap = new HashMap<String, String>();
    for (XNode caseChild : context.getChildren()) {
      String value = caseChild.getStringAttribute("value");
      String resultMap = caseChild.getStringAttribute("resultMap", processNestedResultMappings(caseChild, resultMappings));
      discriminatorMap.put(value, resultMap);
    }
    return builderAssistant.buildDiscriminator(resultType, column, javaTypeClass, jdbcTypeEnum, typeHandlerClass, discriminatorMap);
  }

  /**
   * 5、解析sql片段
   *
   *    <sql id=”userColumns”> id,username,password </sql>
   *
   * mybatis可以根据不同的数据库执行不同的sql，这就是通过sql元素上的databaseId属性来区别的。
   * 同样，首先设置sql元素的id，它必须是当前mapper文件所定义的命名空间。
   * sql元素本身的处理很简单，只是简单的过滤出databaseId和当前加载的配置文件相同的语句以备以后再解析crud遇到时进行引用。
   * 之所以不进行解析，是因为首先能够作为sql元素子元素的所有节点都可以作为crud的子元素，而且sql元素不会在运行时单独使用，
   * 所以也没有必要专门解析一番。下面我们重点来看crud的解析与内部表示。
   * @param list
   * @throws Exception
   */
  private void sqlElement(List<XNode> list) throws Exception {
    if (configuration.getDatabaseId() != null) {
      sqlElement(list, configuration.getDatabaseId());
    }
    sqlElement(list, null);
  }

  private void sqlElement(List<XNode> list, String requiredDatabaseId) throws Exception {
    for (XNode context : list) {
      String databaseId = context.getStringAttribute("databaseId");
      String id = context.getStringAttribute("id");
      id = builderAssistant.applyCurrentNamespace(id, false);
      if (databaseIdMatchesCurrent(id, databaseId, requiredDatabaseId)) {
        sqlFragments.put(id, context);
      }
    }
  }
  
  private boolean databaseIdMatchesCurrent(String id, String databaseId, String requiredDatabaseId) {
    if (requiredDatabaseId != null) {
      if (!requiredDatabaseId.equals(databaseId)) {
        return false;
      }
    } else {
      if (databaseId != null) {
        return false;
      }
      // skip this fragment if there is a previous one with a not null databaseId
      if (this.sqlFragments.containsKey(id)) {
        XNode context = this.sqlFragments.get(id);
        if (context.getStringAttribute("databaseId") != null) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * buildResultMappingFromContext是个公共工具方法，会被反复使用，我们来看下它的具体实现（不是所有元素都包含所有属性）；
   * @param context
   * @param resultType
   * @param flags
   * @return
   * @throws Exception
   */
  private ResultMapping buildResultMappingFromContext(XNode context, Class<?> resultType, List<ResultFlag> flags) throws Exception {
    String property;
    if (flags.contains(ResultFlag.CONSTRUCTOR)) {
      property = context.getStringAttribute("name");
    } else {
      property = context.getStringAttribute("property");
    }
    String column = context.getStringAttribute("column");
    String javaType = context.getStringAttribute("javaType");
    String jdbcType = context.getStringAttribute("jdbcType");
    String nestedSelect = context.getStringAttribute("select");
    // resultMap中可以包含association(一对一)或collection(一对多)复合类型,这些复合类型可以使用外部定义的公用resultMap
    // 或者内嵌resultMap,所以这里的处理逻辑是如果有resultMap就获取resultMap,如果没有,那就动态生成一个。
    // 如果自动生成的话，他的resultMap id通过调用XNode.getValueBasedIdentifier()来获得
    String nestedResultMap = context.getStringAttribute("resultMap",
        processNestedResultMappings(context, Collections.<ResultMapping> emptyList()));
    String notNullColumn = context.getStringAttribute("notNullColumn");
    String columnPrefix = context.getStringAttribute("columnPrefix");
    String typeHandler = context.getStringAttribute("typeHandler");
    String resultSet = context.getStringAttribute("resultSet");
    String foreignColumn = context.getStringAttribute("foreignColumn");
    boolean lazy = "lazy".equals(context.getStringAttribute("fetchType", configuration.isLazyLoadingEnabled() ? "lazy" : "eager"));
    Class<?> javaTypeClass = resolveClass(javaType);
    @SuppressWarnings("unchecked")
    Class<? extends TypeHandler<?>> typeHandlerClass = (Class<? extends TypeHandler<?>>) resolveClass(typeHandler);
    JdbcType jdbcTypeEnum = resolveJdbcType(jdbcType);
    return builderAssistant.buildResultMapping(resultType, property, column, javaTypeClass, jdbcTypeEnum, nestedSelect, nestedResultMap, notNullColumn, columnPrefix, typeHandlerClass, flags, resultSet, foreignColumn, lazy);
  }

  /**
   * resultMap中可以包含association(一对一)或collection(一对多)复合类型,这些复合类型可以使用外部定义的公用resultMap
   * 或者内嵌resultMap,所以这里的处理逻辑是如果有resultMap就获取resultMap,如果没有,那就动态生成一个。
   * 如果自动生成的话，他的resultMap id通过调用XNode.getValueBasedIdentifier()来获得。
   *
   * 由于colletion和association、discriminator里面还可以包含复合类型，所以将进行递归解析直到所有的子元素都为基本列位置，
   * 它在使用层面的目的在于将关系模型映射为对象树模型。
   *
   * <resultMap id="blogResult" type="Blog">
   *   <id property="id" column="blog_id" />
   *   <result property="title" column="blog_title"/>
   *   <association property="author" column="blog_author_id" javaType="Author" resultMap="authorResult"/>
   *   <collection property="posts" javaType="ArrayList" column="id" ofType="Post" select="selectPostsForBlog"/>
   * </resultMap>
   *
   * 注意“ofType”属性，这个属性用来区分JavaBean(或字段)属性类型和集合中存储的对象类型。
   *
   * @param context
   * @param resultMappings
   * @return
   * @throws Exception
   */
  private String processNestedResultMappings(XNode context, List<ResultMapping> resultMappings) throws Exception {
    if ("association".equals(context.getName())
        || "collection".equals(context.getName())
        || "case".equals(context.getName())) {
      // 对于其中的每个非select属性映射，调用resultMapElement进行递归解析。
      if (context.getStringAttribute("select") == null) {
        ResultMap resultMap = resultMapElement(context, resultMappings);
        return resultMap.getId();
      }
    }
    return null;
  }

  private void bindMapperForNamespace() {
    String namespace = builderAssistant.getCurrentNamespace();
    if (namespace != null) {
      Class<?> boundType = null;
      try {
        boundType = Resources.classForName(namespace);
      } catch (ClassNotFoundException e) {
        //ignore, bound type is not required
      }
      if (boundType != null) {
        if (!configuration.hasMapper(boundType)) {
          // Spring may not know the real resource name so we set a flag
          // to prevent loading again this resource from the mapper interface
          // look at MapperAnnotationBuilder#loadXmlResource
          configuration.addLoadedResource("namespace:" + namespace);
          configuration.addMapper(boundType);
        }
      }
    }
  }

}
