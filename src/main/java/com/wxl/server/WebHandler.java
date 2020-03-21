package com.wxl.server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ClassName: WebHandler <br/>
 * Description: <br/>
 * date: 2020/3/21 16:04<br/>
 *
 * @author lenovo<br />
 * @version v1.0
 * @since JDK 1.8
 */
public class WebHandler {
    private List<ServletTag> servletTags;
    private List<ServletMapping> servletMappings;
    private ServletTag servletTag;
    private ServletMapping servletMapping;
    private String temp;
    private InputStream inputStream;

    public WebHandler(String path) throws FileNotFoundException {
        this(new FileInputStream(path));
        System.out.println("--> 接受到路径:"+path);
    }
    public WebHandler(InputStream inputStream){
        this.inputStream = inputStream;
        servletTags = new ArrayList<ServletTag>();
        servletMappings = new ArrayList<ServletMapping>();
    }

    public void handlerWebXml() throws DocumentException {
        SAXReader sax = new SAXReader();
        Document doc = sax.read(inputStream);
        //读取顶级元素
        Element root = doc.getRootElement();
        //读取servelt标签
        List<Element> servlets = root.elements("servlet");
        //读取servletMappinng标签
        List<Element> mappings = root.elements("servlet-mapping");

        System.out.println("--> 开始解析web.xml");
        for(Element element : servlets){
            servletTag = new ServletTag();
            servletTag.setName(element.element("servlet-name").getTextTrim());
            servletTag.setClazz(element.element("servlet-class").getTextTrim());
            System.out.println("--> 解析得到 name："+ element.element("servlet-name").getTextTrim());
            System.out.println("--> 解析得到 class：" + element.element("servlet-class").getTextTrim());
            servletTags.add(servletTag);
        }
        for(Element element : mappings){
            servletMapping = new ServletMapping();
            servletMapping.setName(element.element("servlet-name").getTextTrim());
            List<Element> elements = element.elements("url-pattern");
            Set<String> set = null;
            for (Element element1 : elements){
                set = new HashSet<String>();
                set.add(element1.getTextTrim());
                System.out.println("--> 解析得到 name："+ element.element("servlet-name").getTextTrim());
                System.out.println("--> 解析得到 pattern：" + element1.getTextTrim());
            }
            servletMapping.setPattern(set);
            servletMappings.add(servletMapping);
        }
        System.out.println("--> web.xml解析完成");
    }

    public List<ServletTag> getServletTags() {
        return servletTags;
    }

    public List<ServletMapping> getServletMappings() {
        return servletMappings;
    }
}
