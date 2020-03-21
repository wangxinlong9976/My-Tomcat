package com.wxl.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: Request
 * Description:
 * date: 2020/3/21 11:48
 *
 * @author 王鑫龙
 * @version v1.0
 * @since JDK 1.8
 */
public class Request {
    //请求信息
    private String requestInfo;
    //请求方法    GET POST DELETE PUT ....
    private String method;
    //请求的url
    private String url;
    //请求的参数
    private String requestStr;
    //请求参数的列表
    private Map<String, List<String>> parameterMap;

    //用于流操作
    private int len;
    private byte[] data;

    //常量
    private final String CRLF = "\r\n";

    public Request(InputStream is){
        parameterMap = new HashMap<String, List<String>>();
        data = new byte[1024*50];
        try {
            this.len = is.read(data);
            this.requestInfo = new String(data,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseRequestInfo();
    }

    public Request(Socket socket) throws IOException {
        this(socket.getInputStream());
    }


    /**
     * 解析请求信息
     */
    private void parseRequestInfo(){
        System.out.println(requestInfo);
        System.out.println("--------------开始解析字符串-------------");

        this.method = this.requestInfo.substring(0,this.requestInfo.indexOf("/")).trim();
        System.out.println("--> 解析获得请求方法:"+method);

        int startIndex = requestInfo.indexOf("/")+1;
        int endIndex = requestInfo.indexOf("HTTP/");
        url = requestInfo.substring(startIndex,endIndex).trim();
        System.out.println("--> 解析获得完整请求路径:"+url);

        int paramIndex = url.indexOf("?");
        if(paramIndex>=0){
            String[] urlAndParamArray = url.split("\\?");
            url = urlAndParamArray[0];
            System.out.println("--> 解析获得url:"+url);
            requestStr = urlAndParamArray[1].trim();
            System.out.println("--> 解析获得参数:"+requestStr);
        }
        if(method.equals("POST")){

            String postStr = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
            System.out.println("--> 解析POST 参数:"+postStr);
            if(null == requestStr){
                requestStr = postStr;
            }else{
                requestStr += "&" + postStr;
            }
        }

        requestStr = null == requestStr?"" : requestStr;
        System.out.println("--> 解析最终参数:"+requestStr);

        convertParameterMap();
    }

    /**
     *  解析参数列表
     */
    private void convertParameterMap() {
        if (!requestStr.equals("")) {
            String[] params = requestStr.split("&");
            for(String param: params){
                String[] kv = param.split("=");
                String key = kv[0];
                String value = kv[1] == null?null:decodeURL(kv[1],"utf-8");
                if(!parameterMap.containsKey(key)){
                    parameterMap.put(key, new ArrayList<String>());
                }
                parameterMap.get(key).add(value);
            }
        }
    }

    /**
     *  解码
     *  用于解决url传输中文
     * @param value
     * @param enc
     * @return
     */
    public String decodeURL(String value, String enc){
        try {
            return URLDecoder.decode(value,enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  通过key获取参数列表
     * @param key
     * @return
     */
    public String[] getParameters(String key){
        List<String> values = parameterMap.get(key);
        if(values == null || values.size()<1){
            return null;
        }
        return values.toArray(new String[0]);
    }

    /**
     *  通过key获取参数
     * @param key
     * @return
     */
    public String getParameter(String key){
        String[] values = getParameters(key);
        if(values == null || values.length>1){
            throw new IllegalArgumentException("有多个参数值!");
        }
        return values[0];
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getRequestStr() {
        return requestStr;
    }

    public Map<String, List<String>> getParameterMap() {
        return parameterMap;
    }

    public int getLen() {
        return len;
    }

    public byte[] getData() {
        return data;
    }

    public String getCRLF() {
        return CRLF;
    }
}
