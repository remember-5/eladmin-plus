package me.zhengjie.modules.smsServer.rest;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.keyword.service.KeywordfilteringContentService;
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
    public  String test(@RequestBody  Map<String,Object> information){
        Save save = new Save();
        Soap soap = save.getSoap();
        Save save1 = vailApp(information);
        if(!"0000".equals(save1.getSoap().getCode())){
            return JSONObject.toJSONString(save1);
        }
        JSONObject jsonObject=new JSONObject(information);
        List<Save> saveList =verification(jsonObject);
        for (int i=0;i<saveList.size();i++){
            if (!saveList.get(i).getSoap().getCode().equals(success)){
                return JSONObject.toJSONString(saveList.get(i).getSoap());
            }
        }

        String code = "";
            Map<String, String> map = projectInformationRepository.selectAppid(saveList.get(0).getAppid(),String.valueOf(information.get("secret")));
                for (int i = 0; i < saveList.size(); i++) {//遍历入库
                    SmsaRecord smsaRecord = new SmsaRecord();
                    smsaRecord.setAppid(saveList.get(i).getAppid());
                    smsaRecord.setEntryName(map.get("entry_name"));
                    smsaRecord.setPhone(saveList.get(i).getPhone());
                    smsaRecord.setSign(saveList.get(i).getSign());
                    smsaRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    smsaRecord.setSendTime(new Timestamp(System.currentTimeMillis()));
                    if("0000".equals(saveList.get(i).getSoap().getCode())) {
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
            code = DaHanSmsUtils.batchSubmit(smsParams);
            JSONObject object = JSONObject.parseObject(code);
            if ("0".equals(object.getString("result")) && !"".equals(object.getString("result"))) {
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


    public Save vailApp(Map<String,Object> information){
        Save save=new Save();
        Soap soap = save.getSoap();
        Map<String, String> map = projectInformationRepository.selectAppid(String.valueOf(information.get("appid")),String.valueOf(information.get("secret")));
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
    public List<Save> verification(JSONObject information){
        List<Save> saves=new ArrayList<>();
        JSONArray jsonArray=JSONArray.parseArray(information.get("data").toString());
        String appid=String.valueOf(information.get("appid"));
        String secret= String.valueOf(information.get("secret"));
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            Save save=new Save();
            Soap soap = save.getSoap();
            save.setAppid(appid);
            save.setContent(jsonObject.getString("content"));
            save.setPhone(jsonObject.getString("phone"));
            save.setSign(jsonObject.getString("sign"));
            save.setType("0");
            soap = checkParameter(appid, save.getPhone(), save.getSign(),save.getContent(),secret);
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
