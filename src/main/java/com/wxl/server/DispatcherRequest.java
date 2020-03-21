package com.wxl.server;

import java.io.IOException;
import java.net.Socket;

/**
 * ClassName: DispatcherRequest <br/>
 * Description: <br/>
 * date: 2020/3/21 17:09<br/>
 *
 * @author lenovo<br />
 * @version v1.0
 * @since JDK 1.8
 */
public class DispatcherRequest implements Runnable {
    Socket client;
    Request request;
    Response response;
    Servlet servlet;

    public DispatcherRequest(Socket client){
        this.client = client;
        try {
            request = new Request(client);
            response = new Response(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        servlet = WebApp.getServletByUrl("/"+request.getUrl());
        try {
            if(servlet != null){
                servlet.service(request,response);
                response.pushToBrowser(200);
            }else{
                response.pushToBrowser(404);
            }
        } catch (IOException e) {
            System.out.println("--> 505 Server Error");
            try {
                response.pushToBrowser(505);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
