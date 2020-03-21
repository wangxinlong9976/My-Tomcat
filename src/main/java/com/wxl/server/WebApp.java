package com.wxl.server;

import org.dom4j.DocumentException;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

/**
 * ClassName: WebApp <br/>
 * Description: <br/>
 * date: 2020/3/21 16:31<br/>
 *
 * @author lenovo<br />
 * @version v1.0
 * @since JDK 1.8
 */
public class WebApp {
    private static WebContext context;

    static {
        System.out.println();
        WebHandler webHandler = null;
        try {
            webHandler = new WebHandler("D:\\IdeaProjects\\server\\src\\main\\java\\com\\wxl\\server\\web.xml");
            webHandler.handlerWebXml();
            System.out.println("--> 处理后的servletXml:"+webHandler.getServletTags());
            System.out.println("--> 处理后的servletMappingXml:"+webHandler.getServletMappings());
            context = new WebContext(webHandler.getServletTags(),webHandler.getServletMappings());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static Servlet getServletByUrl(String url){
        String className = context.getClass(url);
        Class servletClass = null;
        Servlet servlet = null;
        try {
            System.out.println("--> 寻找class:"+className);
            servletClass = Class.forName(className);
            servlet = (Servlet)servletClass.getConstructor().newInstance();
            return  servlet;
        } catch (Exception e) {
            System.out.println("--> 404 Not Found");
            return null;
        }

    }

}
