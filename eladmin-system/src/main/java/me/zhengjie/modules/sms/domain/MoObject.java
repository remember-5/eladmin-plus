package me.zhengjie.modules.sms.domain;

import java.io.Serializable;
import java.util.Date;

public class MoObject implements Serializable{
	private static final long serialVersionUID = 924571110173464185L;

	/**
	 * SP编号
	 */
	private String spId;

	/**
	 * SP密码
	 */
	private String spPassword;

	/**
	 * SP服务代码
	 */
	protected String spServiceCode;

	/**
	 * 手机号码所属运营商代码
	 */
	//protected String carrierCode;

	/**
	 * 源地址（手机号码）
	 */
	protected String sourceAddr;

	/**
	 * 目的地址（长号码）
	 */
	protected String destinationAddr;

	/**
	 * 上行消息内容
	 */
	protected String moContent;

	/**
	 * SMPP协议的data_coding字段(消息编码格式)
	 */
	protected Integer dataCoding;

	/**
	 * SMPP协议的esm_class字段
	 */
	protected Integer esmClass;

	/**
	 * SMPP协议的protocol_id字段
	 */
	protected Integer protocolId;

	/**
	 * 上行接收时间
	 */
	protected Date receiveTime;

	/**
	 * SMS上行消息编号（短信平台返回的唯一序列号）
	 */
	protected Long mlinkMoId;

	/**
	 * Mlink接收运营商上行的时间
	 */
	protected String mlinkMoTime;

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getSpServiceCode() {
		return spServiceCode;
	}

	public void setSpServiceCode(String spServiceCode) {
		this.spServiceCode = spServiceCode;
	}

	public String getSpPassword() {
		return spPassword;
	}

	public void setSpPassword(String spPassword) {
		this.spPassword = spPassword;
	}

	public String getSourceAddr() {
		return sourceAddr;
	}

	public void setSourceAddr(String sourceAddr) {
		this.sourceAddr = sourceAddr;
	}

	public String getDestinationAddr() {
		return destinationAddr;
	}

	public void setDestinationAddr(String destinationAddr) {
		this.destinationAddr = destinationAddr;
	}

	public String getMoContent() {
		return moContent;
	}

	public void setMoContent(String moContent) {
		this.moContent = moContent;
	}

	public Integer getDataCoding() {
		return dataCoding;
	}

	public void setDataCoding(Integer dataCoding) {
		this.dataCoding = dataCoding;
	}

	public Integer getEsmClass() {
		return esmClass;
	}

	public void setEsmClass(Integer esmClass) {
		this.esmClass = esmClass;
	}

	public Integer getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(Integer protocolId) {
		this.protocolId = protocolId;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Long getMlinkMoId() {
		return mlinkMoId;
	}

	public void setMlinkMoId(Long mlinkMoId) {
		this.mlinkMoId = mlinkMoId;
	}

	public String getMlinkMoTime() {
		return mlinkMoTime;
	}

	public void setMlinkMoTime(String mlinkMoTime) {
		this.mlinkMoTime = mlinkMoTime;
	}

	private String phone;
	private String content;
	private String subcode;
	private String delivertime;


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getSubcode() {
		return subcode;
	}


	public void setSubcode(String subcode) {
		this.subcode = subcode;
	}


	public String getDelivertime() {
		return delivertime;
	}


	public void setDelivertime(String delivertime) {
		this.delivertime = delivertime;
	}
	/**
	 *  【大汉三通下行状态返回 字段说明】
		result：接口调用结果，说明请参照：5.1.提交响应错误码；
		当result为0时reports字段将出现0到1次，否则reports字段不出现；
		msgid：短信编号；
		phone：下行手机号码；
		status：短信发送结果：
		0——成功；1——接口处理失败；2——运营商网关失败；
		desc：当status为1时，以desc的错误码为准。说明请参照：5.2.状态报告错误码；
		wgcode：当status为2时，表示运营商网关返回的原始值；
		time：状态报告接收时间格式为yyyy-MM-ddHH:mm:ss。
		smsCount：长短信条数。
		smsIndex：长短信第几条标示。
	 */
	private String result;
	private String desc;
	private String reports;
	private String msgid;
	private String status;
	private String wgcode;
	private String time;
	private String smsCount;
	private String smsIndex;

	/**
	 * 天瑞云相关字段
	 * @return
	 */

	private String mobile;
	private String extNo;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getExtNo() {
		return extNo;
	}

	public void setExtNo(String extNo) {
		this.extNo = extNo;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getReports() {
		return reports;
	}

	public void setReports(String reports) {
		this.reports = reports;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWgcode() {
		return wgcode;
	}

	public void setWgcode(String wgcode) {
		this.wgcode = wgcode;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(String smsCount) {
		this.smsCount = smsCount;
	}

	public String getSmsIndex() {
		return smsIndex;
	}

	public void setSmsIndex(String smsIndex) {
		this.smsIndex = smsIndex;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "MoObject [content=" + content + ", dataCoding=" + dataCoding
				+ ", delivertime=" + delivertime + ", desc=" + desc
				+ ", destinationAddr=" + destinationAddr + ", esmClass="
				+ esmClass + ", mlinkMoId=" + mlinkMoId + ", mlinkMoTime="
				+ mlinkMoTime + ", moContent=" + moContent + ", msgid=" + msgid
				+ ", phone=" + phone + ", protocolId=" + protocolId
				+ ", receiveTime=" + receiveTime + ", reports=" + reports
				+ ", result=" + result + ", smsCount=" + smsCount
				+ ", smsIndex=" + smsIndex + ", sourceAddr=" + sourceAddr
				+ ", spId=" + spId + ", spPassword=" + spPassword
				+ ", spServiceCode=" + spServiceCode + ", status=" + status
				+ ", subcode=" + subcode + ", time=" + time + ", wgcode="
				+ wgcode + "]";
	}

}
