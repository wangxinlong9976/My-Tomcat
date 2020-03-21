package com.wxl.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ClassName: Server
 * Description:
 * date: 2020/3/21 0:19
 *
 * @author 王鑫龙
 * @version v1.0
 * @since JDK 1.8
 */
public class Server {
    static ServerSocket serverSocket;
    static boolean serverStatus;


    public static void main(String[] args) {

        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("--> 服务端已开启!");
            serverStatus = true;
            recive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void recive() throws IOException {
        while (serverStatus){
            Socket client = serverSocket.accept();
            System.out.println("--> 服务器监听到一个客户端的连接");
            System.out.println("--> 客户端信息:");
            System.out.println("--> " + client.getRemoteSocketAddress());
            System.out.println();
            new Thread(new DispatcherRequest(client)).start();
        }
    }




}
