package me.zhengjie.modules.smsServer.rest;


import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.keyword.service.KeywordfilteringContentService;
import me.zhengjie.modules.smsServer.domain.Information;
import me.zhengjie.modules.smsServer.domain.Save;
import me.zhengjie.modules.smsServer.domain.SmsaRecord;
import me.zhengjie.modules.smsServer.domain.Soap;
import me.zhengjie.modules.smsServer.repository.ProjectInformationRepository;
import me.zhengjie.modules.smsServer.repository.SmsRecordaRepository;
import me.zhengjie.modules.smsServer.util.DaHanSmsUtils;
import me.zhengjie.modules.smsServer.util.Encryption;
import me.zhengjie.modules.smsServer.util.SmsParam;
import me.zhengjie.utils.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RequestMapping(value = "/sms")
@RestController
@RequiredArgsConstructor
public class SmsController {
    private final SmsRecordaRepository smsRecordaRepository;
    private final ProjectInformationRepository projectInformationRepository;
    private final KeywordfilteringContentService keywordfilteringContentService;

    String success="0000";
    String empty="0001";
    String fangwen="1004";
    String fail="1005";

    /**
     * 大汉三通接口
     * @param
     * @return
     */
    @PostMapping(value = "dahansend")
    public  String test(@RequestBody  List<Information> information){
        Save save = new Save();
        Soap soap = save.getSoap();
        Save save1 = vailAPP(information);
        if(!save1.getSoap().getCode().equals("0000")){
            return JSONObject.toJSONString(save1);
        }
        List<Save> saveList =verification(information);
        for (int i=0;i<saveList.size();i++){
            if (!saveList.get(i).getSoap().getCode().equals(success)){
                return JSONObject.toJSONString(saveList.get(i).getSoap());
            }
        }

        String code = "";
            Map<String, String> map = projectInformationRepository.selectAppid(information.get(0).getAppid(),information.get(0).getSecret());
                for (int i = 0; i < saveList.size(); i++) {//遍历入库
                    SmsaRecord smsaRecord = new SmsaRecord();
                    smsaRecord.setAppid(saveList.get(i).getAppid());
                    smsaRecord.setEntryName(map.get("entry_name"));
                    smsaRecord.setPhone(saveList.get(i).getPhone());
                    smsaRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    smsaRecord.setSendTime(new Timestamp(System.currentTimeMillis()));
                    if(saveList.get(i).getSoap().getCode().equals("0000")) {
                        smsaRecord.setSendStatus("0");
                    }else{
                        smsaRecord.setSendStatus("3");
                        smsaRecord.setSpreat1("该短信内容存在关键字！！");
                    }
                    smsaRecord.setUid(String.valueOf(map.get("id")));
                    smsaRecord.setContent(saveList.get(i).getContent());
                    smsRecordaRepository.save(smsaRecord);
                }
            List<SmsParam> smsParams=new ArrayList<>();
            List<SmsaRecord> smsaRecords = smsRecordaRepository.selectStatus();
            if (smsaRecords.size()>0){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String date = sdf.format(new Date());
                for (int i = 0; i< smsaRecords.size(); i++){
                    SmsParam smsParam=new SmsParam();
                    smsParam.setPhones(smsaRecords.get(i).getPhone());
                    smsParam.setContent(smsaRecords.get(i).getContent());
                    smsParam.setSign(smsaRecords.get(i).getSign());
                    int num = 10000 + new Random().nextInt(900);
                    String msgid="";
                    try {
                        msgid = Encryption.md5Digest(date + num);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    smsParam.setMsgid(msgid);
                    smsParams.add(smsParam);
                }
            }
            code = DaHanSmsUtils.BatchSubmit(smsParams);
            JSONObject object = JSONObject.parseObject(code);
            if (object.getString("result").equals("0") && !object.getString("result").equals("")) {
                for (SmsaRecord smsaRecord : smsaRecords) {
                    smsRecordaRepository.updateStatus(smsaRecord.getId());
                }
                soap.setCode(success);
                soap.setMessage("成功");
                soap.setData(success);
            } else {
                for (SmsaRecord smsaRecord : smsaRecords) {
                    smsRecordaRepository.updatestatefail(smsaRecord.getId());
                }
                soap.setCode(fail);
                soap.setMessage("短信发送失败");
                soap.setData(fail);
                log.info("短信发送失败,返回报文" + code);
            }
            return JSONObject.toJSONString(soap);

    }


    public Save vailAPP(List<Information> information){
        Save save=new Save();
        Soap soap = save.getSoap();
        Map<String, String> map = projectInformationRepository.selectAppid(information.get(0).getAppid(),information.get(0).getSecret());
        if (map.size()<=0){
            soap=save.getSoap();
            soap.setData(fangwen);
            soap.setCode("0004");
            soap.setMessage("appid错误,或密钥错误");
            save.setSoap(soap);
        }else {
            soap.setData(success);
            soap.setCode(success);
            soap.setMessage("成功");
            save.setSoap(soap);
        }
        return save;
    }

    /**
     * 参数验证   遍历手机号内容    过滤敏感词汇
     * @param
     * @return
     */
    public List<Save> verification(List<Information> information){
        List<Save> saves=new ArrayList<>();
        for (Information information1:information) {
            Save save=new Save();
            Soap soap = save.getSoap();
            save.setAppid(information1.getAppid());
            save.setContent(information1.getContent());
            save.setPhone(information1.getPhone());
            save.setSign(information1.getSign());
            save.setType("0");
            soap = checkParameter(information1.getAppid(), information1.getPhone(), information1.getSign(),information1.getContent(),information1.getSecret());
            save.setSoap(soap);
            saves.add(save);
        }
        return saves;
    }

    public Soap checkParameter(String appid,String phone,String sign,String content,String secret){
        Soap soap = new Soap();
        soap.setData(empty);
        if (StringUtils.isEmpty(appid)){
            soap.setCode("1000");
            soap.setMessage("appid不能为空");
            return soap;
        }
        if (StringUtils.isEmpty(content)){
            soap.setCode("1001");
            soap.setMessage("发送内容不能为空");
            return soap;
        }
        if (StringUtils.isEmpty(phone)){
            soap.setCode("1002");
            soap.setMessage("手机号不能为空");
            return soap;
        }
        if (StringUtils.isEmpty(sign)){
            soap.setCode("1003");
            soap.setMessage("签名不能为空");
            return soap;
        }
        if (StringUtils.isEmpty(secret)){
            soap.setCode("1007");
            soap.setMessage("密钥不能为空");
            return soap;
        }
        boolean contains = keywordfilteringContentService.contains(content);
        if (contains){
            soap.setCode("1006");
            soap.setMessage("发送内容含敏感字");
            return soap;
        }
            soap.setData(success);
            soap.setCode(success);
            soap.setMessage("成功");
        return soap;
    }
}
