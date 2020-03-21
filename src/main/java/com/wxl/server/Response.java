package com.wxl.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * ClassName: Response
 * Description: 用于封装响应消息
 * date: 2020/3/20 23:59
 *
 * @author 王鑫龙
 * @version v1.0
 * @since JDK 1.8
 */
public class Response {
    //缓存输出流  用于向页面输出内容
    private BufferedWriter bw;
    //响应内容
    private StringBuilder content;
    //响应头信息
    private StringBuilder headInfo;
    //响应内容的字节数
    private int len;

    //空格
    private final String blank = " ";
    //换行
    private final String  CRLF= "\r\n";

    private Response(){
        content = new StringBuilder();
        headInfo = new StringBuilder();
    }

    public Response(Socket socket){
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()),1024*50);
        } catch (IOException e) {
            headInfo = null;
            e.printStackTrace();
        }
    }

    public Response(OutputStream os){
        this();
        bw = new BufferedWriter(new OutputStreamWriter(os));
    }

    public Response print(String info){
        content.append(info);
        len += info.getBytes().length;
        return this;
    }

    public Response println(String info){
        content.append(info).append(CRLF);
        len += info.getBytes().length;
        return this;
    }

    private void createHeadInfo(int code){
        headInfo.append("HTTP/1.1").append(blank);
        headInfo.append(code).append(blank);
        switch (code){
            case 200:
                headInfo.append("OK").append(CRLF);
                break;
            case 404:
                headInfo.append("Not Found").append(CRLF);
                break;

            case 505:
                headInfo.append("Server Error").append(CRLF);
                break;
        }

        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Server:").append("lhh Server/0.0.1;charset=GBK").append(CRLF);
        headInfo.append("Content-type:text/html").append(CRLF);
        headInfo.append("Content-length:").append(len).append(CRLF);
        headInfo.append(CRLF);
    }


    public void pushToBrowser(int code) throws IOException {
        if(null == headInfo){
            code = 505;
        }
        createHeadInfo(code);
        bw.append(headInfo);
        bw.append(content);
        bw.flush();
    }
}
