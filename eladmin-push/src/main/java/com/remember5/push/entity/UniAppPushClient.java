package com.remember5.push.entity;

import com.getui.push.v2.sdk.common.ApiResult;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UniAppPushClient implements BasePushClient {
    private Set<String> cids;
    private String title;
    private String context;
    private String page;
    private ApiResult<Map<String, Map<String, String>>> returnData;

    private UniAppPushClient() {
    }


    /**
     * 获取推送客户端
     * @param title 标题
     * @param context 内容
     * @param page 页面
     * @return {@link UniAppPushClient}
     */
    public static UniAppPushClient getUniAppPushClient(String title, String context, String page) {
        UniAppPushClient uniAppPushClient = new UniAppPushClient();
        uniAppPushClient.context = context;
        uniAppPushClient.title = title;
        uniAppPushClient.page = page;
        uniAppPushClient.cids = new HashSet<>();
        return uniAppPushClient;
    }

    /**
     * 获取推送客户端
     * @param cid 客户端id
     * @param title 标题
     * @param context 内容
     * @param page 页面
     * @return {@link UniAppPushClient}
     */
    public static UniAppPushClient getUniAppPushClients(String cid, String title, String context, String page) {
        UniAppPushClient uniAppPushClient = new UniAppPushClient();
        uniAppPushClient.cids = new HashSet<>();
        uniAppPushClient.cids.add(cid);
        uniAppPushClient.context = context;
        uniAppPushClient.title = title;
        uniAppPushClient.page = page;
        return uniAppPushClient;
    }

    /**
     * 获取推送客户端
     * @param acids 用户id组
     * @param title 标题
     * @param context 内容
     * @param page 页面
     * @return {@link UniAppPushClient}
     */
    public static UniAppPushClient getUniAppPushClients(Set<String> acids, String title, String context, String page) {
        UniAppPushClient uniAppPushClient = new UniAppPushClient();
        uniAppPushClient.cids = new HashSet<>();
        uniAppPushClient.cids.addAll(acids);
        uniAppPushClient.context = context;
        uniAppPushClient.title = title;
        uniAppPushClient.page = page;
        return uniAppPushClient;
    }

    public Boolean addCid(String cid) {
        cids.add(cid);
        return true;
    }

    public String getTitle() {
        return title;
    }

    public String getContext() {
        return context;
    }

    public String getPage() {
        return page;
    }

    public Set<String> getCids() {
        return cids;
    }

    public Boolean isSuccess() {
        return returnData.isSuccess();
    }

    public void setReturnData(ApiResult<Map<String, Map<String, String>>> returnData) {
        this.returnData = returnData;
    }

}
