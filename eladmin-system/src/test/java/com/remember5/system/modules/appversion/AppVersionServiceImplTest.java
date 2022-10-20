package com.remember5.system.modules.appversion;

import com.alibaba.fastjson.JSON;
import com.remember5.biz.appversion.domain.AppVersion;
import com.remember5.biz.appversion.repository.AppVersionRepository;
import com.remember5.system.SystemApplicationTests;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
class AppVersionServiceImplTest extends SystemApplicationTests {


    @Resource
    AppVersionRepository repository;



    @Test
    @DisplayName("run")
    void run(){
        insertData();
        findAllAndPrint();
//        deleteAll();
    }

    @Test
    @DisplayName("Insert Batch Data")
    void insertData() {
        String appid1 = "naive-ui";
        String appid2 = "weixin";
        ArrayList<AppVersion> appVersions = new ArrayList<>();
        appVersions.add(AppVersion.builder().appId(appid1).appName(appid1).version("1.0.0").build(100L).title("v1.0.0").info("n-transfer 的选项在值变化之后没有重置").minVersion("v1.0.0")
                        .updateType("silent").platform("app(ios&android)").published(1).createTime(new Timestamp(System.currentTimeMillis())).build());
        appVersions.add(AppVersion.builder().appId(appid1).appName(appid1).version("1.0.1").build(101L).title("v1.0.1").info("解决了 n-tag closable 默认值被设为 true 的问题").minVersion("v1.0.0")
                .updateType("silent").platform("app(ios&android)").published(1).createTime(new Timestamp(System.currentTimeMillis())).build());
        appVersions.add(AppVersion.builder().appId(appid1).appName(appid1).version("1.0.2").build(102L).title("v1.0.2").info("多个 naive-ui 共存时定位元素会产生冲突").minVersion("v1.0.0")
                .updateType("silent").platform("app(ios&android)").published(1).createTime(new Timestamp(System.currentTimeMillis())).build());
        appVersions.add(AppVersion.builder().appId(appid1).appName(appid1).version("1.1.0").build(103L).title("v1.1.0").info("n-form-item 的 validate 方法在某些 validator 的返回值下不会 resolve").minVersion("v1.0.0")
                .updateType("silent").platform("app(ios&android)").published(1).createTime(new Timestamp(System.currentTimeMillis())).build());
        appVersions.add(AppVersion.builder().appId(appid1).appName(appid1).version("1.1.1").build(104L).title("v1.1.1").info("修正了 n-data-table 选框列的选框没有垂直居中的问题").minVersion("v1.0.0")
                .updateType("silent").platform("app(ios&android)").published(1).createTime(new Timestamp(System.currentTimeMillis())).build());
        appVersions.add(AppVersion.builder().appId(appid1).appName(appid1).version("1.2.0").build(105L).title("v1.2.0").info("修正了 line 型 n-tabs 线不随 activeName 属性改变的问题").minVersion("v1.1.0")
                .updateType("silent").platform("app(ios&android)").published(1).createTime(new Timestamp(System.currentTimeMillis())).build());


        appVersions.add(AppVersion.builder().appId(appid2).appName(appid2).version("1.0.0").build(100L).title("v1.0.0").info("可以在“我的银行卡”中充值话费").minVersion("v1.0.0")
                .updateType("silent").platform("app(ios&android)").published(1).createTime(new Timestamp(System.currentTimeMillis())).build());
        appVersions.add(AppVersion.builder().appId(appid2).appName(appid2).version("1.0.1").build(101L).title("v1.0.1").info("可以转发多条聊天记录，或者通过邮件备份").minVersion("v1.0.0")
                .updateType("silent").platform("app(ios&android)").published(1).createTime(new Timestamp(System.currentTimeMillis())).build());
        appVersions.add(AppVersion.builder().appId(appid2).appName(appid2).version("1.0.2").build(102L).title("v1.0.2").info("表情商店，有趣好玩的表情在这里").minVersion("v1.0.0")
                .updateType("silent").platform("app(ios&android)").published(1).createTime(new Timestamp(System.currentTimeMillis())).build());
        appVersions.add(AppVersion.builder().appId(appid2).appName(appid2).version("1.1.0").build(103L).title("v1.1.0").info("“扫一扫”，可以扫条码、图书和CD封面、街景，还可以翻译英文单词").minVersion("v1.0.0")
                .updateType("silent").platform("app(ios&android)").published(1).createTime(new Timestamp(System.currentTimeMillis())).build());
        appVersions.add(AppVersion.builder().appId(appid2).appName(appid2).version("1.1.1").build(104L).title("v1.1.1").info("可以在聊天中和朋友共享实时位置，还能一起对讲").minVersion("v1.0.0")
                .updateType("silent").platform("app(ios&android)").published(1).createTime(new Timestamp(System.currentTimeMillis())).build());
        appVersions.add(AppVersion.builder().appId(appid2).appName(appid2).version("1.2.0").build(105L).title("v1.2.0").info("发朋友圈时，可以附上你所在的餐馆或景点").minVersion("v1.1.0")
                .updateType("silent").platform("app(ios&android)").published(1).createTime(new Timestamp(System.currentTimeMillis())).build());

        repository.saveAll(appVersions);
    }


    @Test
    void findAllAndPrint() {
        final List<AppVersion> all = repository.findAll();
        all.forEach(e->{
            System.err.println(JSON.toJSONString(e));
        });
    }


    @Test
    @Disabled
    @DisplayName("Delete All Data")
    void deleteAll(){
        repository.deleteAll();
    }


}
