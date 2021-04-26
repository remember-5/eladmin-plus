package me.zhengjie.modules.smsServer.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
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


	private String phone;
	private String content;
	private String subcode;
	private String delivertime;


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


}
