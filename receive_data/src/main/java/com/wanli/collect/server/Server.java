package com.wanli.collect.server;

import com.wanli.collect.service.DataService;
import com.wanli.collect.service.impl.DataServiceImpl;
import com.wanli.collect.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.*;

/**
 * Create By HU
 * Date 2019/1/5 14:23
 */

@Slf4j
public class Server {

    //线程池创建
    private static ExecutorService executor = Executors.newFixedThreadPool(100);

    private static final DataService dataService = DataServiceImpl.getInstance();

    public static void main(String[] args) {

        try {
            DatagramSocket socket = new DatagramSocket(1234);
            byte[] container = new byte[12];

            while (true) {
                DatagramPacket packet = new DatagramPacket(container,container.length);
                //在接收到数据之前，一直阻塞
                socket.receive(packet);

                executor.execute(() -> {

                    //接收数据
                    byte[] data = packet.getData();
                    String msg = StringUtils.bytesToHexString(data);
                    //String msg = "00010106000100010013F796";
                    if(msg == null || "".equals(msg)) {
                        throw new RuntimeException("数据为空");
                    }
                    msg = msg.toUpperCase();
                    log.info("=========> receive data : {}" , msg);
                    //校验数据
                    String actionNum = msg.substring(4, 6);
                    String dataLength = msg.substring(6, 8);

                    String crc = StringUtils.getCRC(StringUtils.hexStringToBytes(msg.substring(0, msg.length() - 4)));
                    if ((crc.toUpperCase()).equals(StringUtils.convertCRC(msg).toUpperCase()) && "01".equals(actionNum) && "06".equals(dataLength)) {
                        //调用服务层方法，处理数据
                        try {
                            dataService.save(msg);
                            log.info("<========= data receive success: {}" , msg);
                        } catch (Exception e) {
                            log.error("<========= have error : {}" , e.getMessage());
                        }
                    } else {
                        log.info("<========= data format error : {}" , msg);
                    }

                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
