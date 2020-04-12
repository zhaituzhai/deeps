package org.mybatis.internal.example.init;

import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.mybatis.internal.example.pojo.User;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Mybatis 初始化的六个工具
 * 1、ObjectFactory
 *
 * @author zhaojm
 * @date 2020/4/12 21:43
 */
public class InitMybatisTest {

    public static void main(String[] args) throws Exception {
        XmlParserTest();
    }

    /**
     * XPath、EntityResolver
     */
    private static void XmlParserTest() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setValidating(false);

        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        builder.setEntityResolver(new XMLMapperEntityResolver());
        InputSource inputSource = new InputSource(Resources.getResourceAsStream("org/mybatis/internal/example/Configuration.xml"));

        Document document = builder.parse(inputSource);

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        String value = (String) xpath.evaluate("/configuration/settings/setting[@name='defaultStatementTimeout']/@value", document, XPathConstants.STRING);

        System.out.println("defaultStatementTimeout=\"" + value + "\"");

        Node node = (Node) xpath.evaluate("/configuration/mappers/mapper[1]", document, XPathConstants.NODE);
        NamedNodeMap attributeNodes = node.getAttributes();
        for (int i = 0; i < attributeNodes.getLength(); i++) {
            Node n = attributeNodes.item(i);
            System.out.println(n.getNodeName() + "=\"" + n.getNodeValue() + "\"");
        }
    }

    /**
     * ObjectFactory Reflector、Invoker、ReflectorFactory
     */
    private static void ClassUtilTest() throws IllegalAccessException, InvocationTargetException {
        // 使用默认构造方法，反射创建一个Student对象，反射获得studId属性并赋值为20，System.out输出studId的属性值。
        ObjectFactory factory = new DefaultObjectFactory();
        User user = factory.create(User.class);
        Reflector reflector = new Reflector(User.class);
        Invoker userIdInvoker = reflector.getSetInvoker("userId");
        userIdInvoker.invoke(user, new Object[]{20});
        userIdInvoker = reflector.getGetInvoker("userId");
        System.out.println(userIdInvoker.invoke(user, null));
    }

}
