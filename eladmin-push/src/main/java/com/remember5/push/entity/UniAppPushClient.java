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

    public static UniAppPushClient GetUniAppPushClient(String title, String context, String page) {
        UniAppPushClient uniAppPushClient = new UniAppPushClient();
        uniAppPushClient.context = context;
        uniAppPushClient.title = title;
        uniAppPushClient.page = page;
        uniAppPushClient.cids = new HashSet<>();
        return uniAppPushClient;
    }

    public static UniAppPushClient GetUniAppPushClients(String cid, String title, String context, String page) {
        UniAppPushClient uniAppPushClient = new UniAppPushClient();
        uniAppPushClient.cids = new HashSet<>();
        uniAppPushClient.cids.add(cid);
        uniAppPushClient.context = context;
        uniAppPushClient.title = title;
        uniAppPushClient.page = page;
        return uniAppPushClient;
    }

    public static UniAppPushClient GetUniAppPushClients(Set<String> acids, String title, String context, String page) {
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
