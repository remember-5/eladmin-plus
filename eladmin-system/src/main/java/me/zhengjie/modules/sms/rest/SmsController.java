package me.zhengjie.modules.sms.rest;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.sms.domain.ProjectInformation;
import me.zhengjie.modules.sms.domain.Save;
import me.zhengjie.modules.sms.domain.SmsRecord;
import me.zhengjie.modules.sms.domain.Soap;
import me.zhengjie.modules.sms.repository.ProjectInformationRepository;
import me.zhengjie.modules.sms.repository.SmsRecordRepository;
import me.zhengjie.modules.sms.util.DaHanSmsUtils;
import me.zhengjie.modules.sms.util.Encryption;
import me.zhengjie.modules.sms.util.SmsParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RequestMapping(value = "/sms")
@RestController
@RequiredArgsConstructor
public class SmsController {
    private final SmsRecordRepository smsRecordRepository;
    private final ProjectInformationRepository projectInformationRepository;

    /**
     * 大汉三通接口
     * @param request
     * @return
     */
    @PostMapping(value = "DaHanSend", produces = "application/json;charset=UTF-8")
    public synchronized String test(HttpServletRequest request){
        Save save=verification(request);
        Soap soap = save.getSoap();
        String code = "";
        if (soap.getData().equals("0000")){
            Map<String, String> map = projectInformationRepository.selectentryName(save.getAppid());
            if (map.size()>0) {
                String[] split = save.getPhone().split(",");
                for (int i = 0; i < split.length; i++) {//遍历入库
                    SmsRecord smsRecord = new SmsRecord();
                    smsRecord.setAppid(save.getAppid());
                    smsRecord.setEntryName(map.get("entry_name"));
                    smsRecord.setPhone(save.getPhone());
                    smsRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    smsRecord.setSendTime(new Timestamp(System.currentTimeMillis()));
                    smsRecord.setSendStatus("0");
                    smsRecord.setSpreat("大汉三通");
                    smsRecord.setContent(save.getContent());
                    smsRecordRepository.save(smsRecord);
                }
            }else {
                log.info("name值为"+map.get("entry_name"));
            }
            List<SmsParam> smsParams=new ArrayList<>();
            List<SmsRecord> smsRecords = smsRecordRepository.selectStatus();
            if (smsRecords.size()>0){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String date = sdf.format(new Date());
                for (int i=0;i<smsRecords.size();i++){
                    SmsParam smsParam=new SmsParam();
                    smsParam.setPhones(smsRecords.get(i).getPhone());
                    smsParam.setContent(smsRecords.get(i).getContent());
                    smsParam.setSign(smsRecords.get(i).getSign());
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
                for (SmsRecord smsRecord : smsRecords) {
                    smsRecordRepository.updateStatus(smsRecord.getId());
                }
                soap.setCode("0000");
                soap.setMessage("成功");
                soap.setData("0000");
            } else {
                for (SmsRecord smsRecord : smsRecords) {
                    smsRecordRepository.updatestatefail(smsRecord.getId());
                }
                soap.setCode("1005");
                soap.setMessage("短信发送失败");
                soap.setData("1005");
                log.info("短信发送失败,返回报文" + code);
            }
            return JSONObject.toJSONString(soap);
        }else {
            log.info("参数效验失败"+soap.getCode()+soap.getData()+soap.getMessage());
            return JSONObject.toJSONString(soap);
        }
       /* JSONObject json=new JSONObject();
        json.put("code","9999");
        json.put("message","请求异常");
        json.put("data","9999");
        return JSONObject.toJSONString(json);*/
    }

    /**
     * 验证
     * @param request
     * @return
     */
    public Save verification(HttpServletRequest request){
        Save save=new Save();
        Soap soap = save.getSoap();
        soap.setData("0001");
        String appid=request.getParameter("appid");
        String sign=request.getParameter("sign");
        String phone=request.getParameter("phone");
        String content=request.getParameter("content");
        String type=request.getParameter("type")==null?"0":request.getParameter("type");
        save.setAppid(appid);
        save.setContent(content);
        save.setPhone(phone);
        save.setSign(sign);
        save.setType(type);
        soap=checkParameter(appid,phone,sign,content);
        if (!soap.getData().equals("0000")){//如果参数为空，则直接返回错误信息
                save.setSoap(soap);
                return save;
        }else {
            soap=save.getSoap();
            soap.setData("0001");
        }
        List<ProjectInformation> projectInformations = projectInformationRepository.selectAppid(save.getAppid());
        if (projectInformations.size()<=0){
            soap=save.getSoap();
            soap.setData("1004");
            soap.setCode("0004");
            soap.setMessage("请联系管理员添加appid访问");
        }else {
            soap.setData("0000");
            soap.setCode("0000");
            soap.setMessage("成功");
        }
        return save;
    }

    public Soap checkParameter(String appid,String phone,String sign,String content){
        Soap soap = new Soap();
        soap.setData("0001");
        if (appid==null||appid.trim().equals("")){
            soap.setCode("1000");
            soap.setMessage("appid不能为空");
            return soap;
        }
        if (content==null||content.trim().equals("")){
            soap.setCode("1001");
            soap.setMessage("发送内容不能为空");
            return soap;
        }
        if (phone==null||phone.trim().equals("")){
            soap.setCode("1002");
            soap.setMessage("手机号不能为空");
            return soap;
        }
        if (sign==null||sign.trim().equals("")){
            soap.setCode("1003");
            soap.setMessage("签名不能为空");
            return soap;
        }
            soap.setData("0000");
            soap.setCode("0000");
            soap.setMessage("成功");
        return soap;
    }
}
