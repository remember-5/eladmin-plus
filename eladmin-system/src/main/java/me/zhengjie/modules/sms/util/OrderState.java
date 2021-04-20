package me.zhengjie.modules.sms.util;

public enum OrderState {

	Activtiy_Created(0),//新建
	Activtiy_Submitted(1),//已提交
	Activtiy_Succeed(2),//已完成
	Activtiy_Closed(3),//已关闭
	Activtiy_Failed(4),//失败
	Activtiy_OperatorClosed(5),//运营商关闭
	CUSTOMER_Succeed(1),	// 1已发送  2已成功 3已失败  4回复创建
	CUSTOMER_Failed(3),
	CUSTOMER_SubmitFailed(3),
	Single(1), 	//单发
	Multi(2);	//群发



	private final int value;

	OrderState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
