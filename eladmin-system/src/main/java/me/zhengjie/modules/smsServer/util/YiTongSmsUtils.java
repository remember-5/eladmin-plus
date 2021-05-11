/*
 * $Id: DIYClientExample.java 1600 2009-06-23 02:38:46Z hyyang $
 * $Revision: 1600 $
 * $Date: 2009-06-23 10:38:46 +0800 (鏄熸湡浜? 23 鍏湀 2009) $
 */

package me.zhengjie.modules.smsServer.util;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.smsServer.domain.MoObject;
import me.zhengjie.modules.smsServer.domain.SmsPushEtongnet;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 移通短信发送接口
 * Mlink下行请求java示例 <br>
 * <Ul>
 * <Li>本示例定义几种下行请求消息的使用方法</Li>
 * <Li>本示例支持 jre1.4 或以上版本</Li>
 * <Li>本示例不依赖于除jre lib之外的jar包</Li>
 * </Ul>
 * @author hyyang
 * @since 1.4
 */
@Slf4j
public class YiTongSmsUtils {

	private static final String MT_URL="http://esms90.10690007.net/sms/mt";  //下行地址
    private static final String MO_URL="http://polling2.10690007.net/sms/mortq";    //主动获取上行地址
    private static final String SPID = "20454";		//账号
    private static final String PASSWORD = "RNsYw8Nk";			//密码
    private static final String MT_REQUEST = "MT_REQUEST"; 	 	//发送单一号码单一内容  指令
    private static final String MULTI_MT_RESPONSE = "MULTI_MT_REQUEST"; 	//群发多个号码同一内容 指令
    private static final String MOQ_REQUEST = "MOQ_REQUEST"; //主动获取上行指令
    private static final String SPSC = "00"; 		//sp服务代码，可选参数，默认为 00
    private static final String SA = ""; 		//源号码，可选
    private static final int DC = 15; 		//源号码，可选参数


    /**
     * 相同内容短信发送方法
     * @param phone
     * @param smsMessages
     * @param smsSign
     * @return
     */
    @SuppressWarnings("unchecked")
	public static HashMap SingleMt(String phone, String smsMessages, int type, SmsPushEtongnet smsSign) {
    	String sm = encodeHexStr(DC, smsMessages);//下行内容进行Hex编码，此处dc设为15，即使用GBK编码格式
        StringBuffer sb = new StringBuffer();
        sb.append(MT_URL).append("?command=");
        if(type == OrderState.Single.getValue()){
        	sb.append(MT_REQUEST);
        	sb.append("&sa=").append(SA);
        	sb.append("&da=").append(phone);
        } else {
        	sb.append(MULTI_MT_RESPONSE);
        	sb.append("&das=").append(phone);
        }
        sb.append("&spid=").append(smsSign.getSpid());
        sb.append("&sppassword=").append(smsSign.getPassword());
        sb.append("&spsc=").append(SPSC);
        sb.append("&sm=").append(sm);
        sb.append("&dc=").append(DC);
        //组成url字符串
        String smsUrl = sb.toString();
        //发送http请求，并接收http响应
        log.info("移通接口短信---发送短信请求报文："+smsUrl);
        String resStr = doGetRequest(smsUrl);
        log.info("移通接口短信---发送短信返回报文："+resStr);
        //解析响应字符串
        HashMap hashMap = parseResStr(resStr);
		return hashMap;
    }

    /**
     * 获取上行内容方法
     */
    public static List<MoObject> MultiMo() {
    	List<MoObject> moObjectList = new ArrayList<MoObject>();
    	try {
    		String smsUrl = MO_URL + "?command=" + MOQ_REQUEST + "&spid=" + SPID + "&sppassword=" + PASSWORD;
	        //发送http请求，并接收http响应
    		log.info("移通接口短信---获取上行请求报文："+ smsUrl.toString());
	        String resStr = doGetRequest(smsUrl.toString());
	        log.info("移通接口短信---获取上行返回报文："+resStr);
	        HashMap pp = parseResStr(resStr);
	        String mos = (String) pp.get("mos");
	        //解析响应字符串
	        if (mos != null && mos.length() > 0) {
				String[] moarray = mos.split(",");
				for (String mostr : moarray) {
					String[] mo = mostr.split("/");
					MoObject moObject = new MoObject();
					moObject.setSpId(SPID);
					moObject.setSpServiceCode(SPSC);
					moObject.setMlinkMoId(Long.parseLong(mo[0]));
					moObject.setSourceAddr(mo[1].substring(2, 13));
					moObject.setDestinationAddr(mo[2]);
					moObject.setDataCoding(Integer.parseInt(mo[5]));
					moObject.setMoContent(decodeHexString(moObject.getDataCoding(), mo[6]));
					if (mo[3] != null && mo[3].length() > 0) {
						moObject.setEsmClass(Integer.parseInt(mo[3]));
					} else {
						moObject.setEsmClass(0);
					}
					if (mo[4] != null && mo[4].length() > 0) {
						moObject.setProtocolId(Integer.parseInt(mo[4]));
					} else {
						moObject.setProtocolId(0);
					}
					moObject.setReceiveTime(new Date());
					moObjectList.add(moObject);
				}
			}
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
    	} catch (DecoderException e) {
    		e.printStackTrace();
    	}
		return moObjectList;
    }

    //以下代码为定义的工具方法或变量，可单独在一个工具类中进行定义

    /**
     * 发送http GET请求，并返回http响应字符串
     *
     * @param urlstr 完整的请求url字符串
     * @return
     */
    public static String doGetRequest(String urlstr) {
        String res = null;
        try {
            URL url = new URL(urlstr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Content-Type", "text/html; charset=GB2312");
//            httpConn.setConnectTimeout(5000);//jdk 1.5换成这个,连接超时
//            httpConn.setReadTimeout(10000);//jdk 1.5换成这个,读操作超时
            httpConn.setDoInput(true);
            int rescode = httpConn.getResponseCode();
            if (rescode == 200) {
                BufferedReader bfw = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                res = bfw.readLine();
            }
        } catch (Exception e) {
        	e.printStackTrace();
            log.info("移通接口请求异常！"+e);
        }
        return res;
    }

    /**
     * 发送http POST请求，并返回http响应字符串
     *
     * @param urlstr 完整的请求url字符串
     * @return
     */
    public static String doPostRequest(String urlstr, String dasms) {
        String res = null;
        try {
            URL url = new URL(urlstr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=ISO-8859-1");
            OutputStream outputstream = httpConn.getOutputStream();
            outputstream.write(("dasm=" + dasms).getBytes());
            outputstream.flush();
            httpConn.setConnectTimeout(5000);//jdk 1.5换成这个,连接超时
            httpConn.setReadTimeout(5000);//jdk 1.5换成这个,读操作超时

            int rescode = httpConn.getResponseCode();
            if (rescode == 200) {
                BufferedReader bfw = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                res = bfw.readLine();
            }
        } catch (Exception e) {
        	e.printStackTrace();
            log.info("移通接口请求异常！"+e);
        }
        return res;
    }

    /**
     * 将 短信下行 请求响应字符串解析到一个HashMap中
     * @param resStr
     * @return
     */
    public static HashMap parseResStr(String resStr) {
        HashMap pp = new HashMap();
        try {
            String[] ps = resStr.split("&");
            for (int i = 0; i < ps.length; i++) {
                int ix = ps[i].indexOf("=");
                if (ix != -1) {
                    pp.put(ps[i].substring(0, ix), ps[i].substring(ix + 1));
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return pp;
    }


    /**
     * Hex编码字符组
     */
    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 将普通字符串转换成Hex编码字符串
     *
     * @param dataCoding 编码格式，15表示GBK编码，8表示UnicodeBigUnmarked编码，0表示ISO8859-1编码
     * @param realStr 普通字符串
     * @return Hex编码字符串
     */
    public static String encodeHexStr(int dataCoding, String realStr) {
        String hexStr = null;

        if (realStr != null) {
            byte[] data = null;
            try {
                if (dataCoding == 15) {
                    data = realStr.getBytes("GBK");
                } else if ((dataCoding & 0x0C) == 0x08) {
                    data = realStr.getBytes("UnicodeBigUnmarked");
                } else {
                    data = realStr.getBytes("ISO8859-1");
                }
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.toString());
            }

            if (data != null) {
                int len = data.length;
                char[] out = new char[len << 1];
                // two characters form the hex value.
                for (int i = 0, j = 0; i < len; i++) {
                    out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
                    out[j++] = DIGITS[0x0F & data[i]];
                }
                hexStr = new String(out);
            }
        }
        return hexStr;
    }

    /**
     * 将Hex编码字符串还原成普通字符串
     *
     * @param dataCoding 反编码格式，15表示GBK编码，8表示UnicodeBigUnmarked编码，0表示ISO8859-1编码
     * @param hexStr Hex编码字符串
     * @return 普通字符串
     */
    public static String decodeHexStr(int dataCoding, String hexStr) {
        String realStr = null;

        if (hexStr != null) {
            char[] data = hexStr.toCharArray();

            int len = data.length;

            if ((len & 0x01) != 0) {
                throw new RuntimeException("Odd number of characters.");
            }

            byte[] out = new byte[len >> 1];

            // two characters form the hex value.
            for (int i = 0, j = 0; j < len; i++) {
                int f = Character.digit(data[j], 16) << 4;
                if(f==-1){
                    throw new RuntimeException("Illegal hexadecimal charcter " + data[j] + " at index " + j);
                }
                j++;
                f = f | Character.digit(data[j], 16);
                if(f==-1){
                    throw new RuntimeException("Illegal hexadecimal charcter " + data[j] + " at index " + j);
                }
                j++;
                out[i] = (byte) (f & 0xFF);
            }
            try {
                if (dataCoding == 15) {
                    realStr = new String(out, "GBK");
                } else if ((dataCoding & 0x0C) == 0x08) {
                    realStr = new String(out, "UnicodeBigUnmarked");
                } else {
                    realStr = new String(out, "ISO8859-1");
                }
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.toString());
            }
        }

        return realStr;
    }

    public static String decodeHexString(int dataCoding, String hexStr) throws DecoderException {
		String realStr = null;
		try {
			if (hexStr != null) {
				if (dataCoding == 15) {
					realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "GBK");
				} else if ((dataCoding & 0x0C) == 0x08) {
					realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "UnicodeBigUnmarked");
				} else {
					realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "ISO8859-1");
				}
			}
		} catch (UnsupportedEncodingException e) {
			//logger.error(e.getMessage(), e);
		}
		return realStr;
	}

}
