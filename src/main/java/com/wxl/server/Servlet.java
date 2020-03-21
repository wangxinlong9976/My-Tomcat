package com.wxl.server;

/**
 * ClassName: Servlet
 * Description:
 * date: 2020/3/21 16:55
 *
 * @author lenovo
 * @version v1.0
 * @since JDK 1.8
 */
public interface Servlet {

    public void service(Request request,Response response);
}
