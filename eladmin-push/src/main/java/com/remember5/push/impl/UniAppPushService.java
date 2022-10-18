package com.remember5.push.impl;

import cn.hutool.core.lang.UUID;
import com.getui.push.v2.sdk.ApiHelper;
import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.Audience;
import com.getui.push.v2.sdk.dto.req.AudienceDTO;
import com.getui.push.v2.sdk.dto.req.message.PushChannel;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.getui.push.v2.sdk.dto.req.message.PushMessage;
import com.getui.push.v2.sdk.dto.req.message.android.GTNotification;
import com.getui.push.v2.sdk.dto.req.message.ios.Alert;
import com.getui.push.v2.sdk.dto.req.message.ios.Aps;
import com.getui.push.v2.sdk.dto.req.message.ios.IosDTO;
import com.getui.push.v2.sdk.dto.res.TaskIdDTO;
import com.remember5.push.PushService;
import com.remember5.push.entity.UniAppPushClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @author wangjiahao
 * @date 2022/5/27 19:23
 */
@Slf4j
@Service
public class UniAppPushService implements PushService<UniAppPushClient> {

    @Autowired(required = false)
    private ApiHelper apiHelper;

    @Override
    public boolean push(UniAppPushClient cid) {
        return mypush(cid.getCids(), cid.getTitle(), cid.getContext(), cid.getPage());
    }

    @Override
    public boolean batchPush(Set<UniAppPushClient> cids) {
        return false;
    }

    private boolean mypush(Set<String> cids, String title, String context, String page) {
        //根据cid进行单推
        PushDTO<Audience> pushDTO = new PushDTO<>();
        // 设置推送参数,应该是防止重复推送
        pushDTO.setRequestId(System.currentTimeMillis() + "");
        /**** 设置个推通道参数 *****/
        PushMessage pushMessage = new PushMessage();
        pushDTO.setPushMessage(pushMessage);
        GTNotification notification = new GTNotification();
        pushMessage.setNotification(notification);
        notification.setTitle(title);
        notification.setBody(context);
        String toPageName = page;
        if (toPageName != null) {
            notification.setClickType("intent");
            notification.setPayload(toPageName);
            notification.setIntent("intent:#Intent;action=android.intent.action.oppopush;launchFlags=0x14000000;component=com.bjgonglian.zxqyzc/io.dcloud.PandoraEntry;S.UP-OL-SU=true;S.title=测试标题;S.content=" + toPageName + ";S.payload=toPage;end");
        } else {
            notification.setClickType("none");
        }
        /**** 设置个推通道参数，更多参数请查看文档或对象源码 *****/

        /**** 设置厂商相关参数 ****/
        PushChannel pushChannel = new PushChannel();
        pushDTO.setPushChannel(pushChannel);
        /*设置ios厂商参数*/
        IosDTO iosDTO = new IosDTO();
        iosDTO.setPayload(toPageName);
        pushChannel.setIos(iosDTO);
        // 相同的collapseId会覆盖之前的消息
        iosDTO.setApnsCollapseId(UUID.randomUUID().toString());
        Aps aps = new Aps();
        iosDTO.setAps(aps);
        Alert alert = new Alert();
        aps.setAlert(alert);
        alert.setTitle(title);
        alert.setBody(context);
        Audience audience = new Audience();
        pushDTO.setAudience(audience);
        cids.forEach(item -> {
            audience.addCid(item);
        });
        PushApi pushApi = apiHelper.creatApi(PushApi.class);
        ApiResult<TaskIdDTO> msg = pushApi.createMsg(pushDTO);
        AudienceDTO audienceDTO = new AudienceDTO();
        audienceDTO.setTaskid(msg.getData().getTaskId());
        audienceDTO.setAudience(audience);
        ApiResult<Map<String, Map<String, String>>> mapApiResult = pushApi.pushListByCid(audienceDTO);
        if (mapApiResult.isSuccess()) {
            mapApiResult.getData();
            log.info(mapApiResult.getData().toString());
            return true;
        }
        log.error(mapApiResult.getData().toString());
        return false;
    }


}
