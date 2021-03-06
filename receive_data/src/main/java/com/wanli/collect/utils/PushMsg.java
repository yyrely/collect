package com.wanli.collect.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By HU
 * Date 2019/1/5 18:45
 */

public class PushMsg {

    private static final Logger LOG = LoggerFactory.getLogger(PushMsg.class);
    private static final String APP_KEY ="f8146df72f1c70473f316064";
    private static final String MASTER_SECRET = "08d0664b7463d33abe3a8642";

    public static void testSendPush(String msg,String applicationFlag) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);

        final PushPayload payload = buildPushObject(msg,applicationFlag);
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
    }

    public static PushPayload buildPushObject_all_all_alert(String msg) {
        return PushPayload.alertAll(msg);
    }

    public static PushPayload buildPushObject_android_and_ios(String msg) {
        Map<String, String> extras = new HashMap<String, String>();
        extras.put("test", "https://community.jiguang.cn/push");
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert("消息")
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("消防警报")
                                .addExtras(extras).build())

                        .addPlatformNotification(IosNotification.newBuilder()
                                .setBadge(1)
                                .setSound("sound.wav")
                                .addExtra("extra_key", "extra_value").build())
                        .build()).setMessage(Message.content("test"))
                .build();
    }

    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert("通知")
                                .setBadge(1)
                                .setSound("sound.wav")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                .setMessage(Message.content("消息"))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    private static PushPayload buildPushObject(String msg,String applicationFlag) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(applicationFlag))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                            .autoBadge()
                            .setAlert(msg)
                            .build())
                        .build())
                .build();
    }
}
