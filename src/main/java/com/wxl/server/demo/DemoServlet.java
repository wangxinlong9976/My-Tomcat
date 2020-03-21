package com.wxl.server.demo;

import com.wxl.server.Request;
import com.wxl.server.Response;
import com.wxl.server.Servlet;

/**
 * ClassName: DemoServlet <br/>
 * Description: <br/>
 * date: 2020/3/21 17:28<br/>
 *
 * @author lenovo<br />
 * @version v1.0
 * @since JDK 1.8
 */
public class DemoServlet implements Servlet {

    public void service(Request request, Response response) {
        String name = request.getParameter("name");
        response.print("<html><head>\n" +
                "\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "\n</head>");
        response.print("<body><h3>你好  </h3>"+name);
        response.print("<input type='button' onclick="+"alert('测试响应数据')"+" />");
        response.print("</body></html>");
        System.out.println("--> 获取到参数:"+name);
    }
}
