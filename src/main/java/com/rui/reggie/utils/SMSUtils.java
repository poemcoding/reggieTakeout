package com.rui.reggie.utils;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;

/**
 * 短信发送工具类
 */
public class SMSUtils {
    //
//	/**
//	 * 发送短信
//	 * @param signName 签名
//	 * @param templateCode 模板
//	 * @param phoneNumbers 手机号
//	 * @param param 参数
//	 */
    public static void sendMessage(String signName, String templateCode, String phoneNumbers, String param) {
        // 短信应用 SDK AppID
        int appid = 1400111111; // SDK AppID 以1400开头
        // 短信应用 SDK AppKey
        String appkey = "*******************";

        // 短信模板 ID，需要在短信应用中申请
        int templateId = 7839; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
        // 签名
        String smsSign = "lambert"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请

        try {
            String[] params = {param,"5"};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers,
                    templateId, params, smsSign, "", "");
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }

    }
}
//
//}
