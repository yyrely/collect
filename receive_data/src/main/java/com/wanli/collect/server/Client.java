package com.wanli.collect.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Create By HU
 * Date 2019/1/5 15:10
 */

public class Client {

    public static void main(String[] args){

        for (int i = 0; i < 2; i++){
            new Thread(() -> {
                for(int j = 0; j < 10; j++) {
                    try {
                        DatagramSocket datagramSocket = new DatagramSocket();
                        //准备数据，把数据封装到数据包中。
                        String data = Thread.currentThread().getName() + "-msg.";
                        //创建了一个数据包
                        DatagramPacket packet = new DatagramPacket(data.getBytes(), data.getBytes().length, InetAddress.getLocalHost() , 1234);
                        //调用udp的服务发送数据包
                        datagramSocket.send(packet);
                        //关闭资源 ---实际上就是释放占用的端口号
                        datagramSocket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
