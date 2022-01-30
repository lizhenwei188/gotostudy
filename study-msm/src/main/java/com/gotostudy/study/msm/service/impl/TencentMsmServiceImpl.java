package com.gotostudy.study.msm.service.impl;

import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.msm.service.TencentMsmService;
import com.gotostudy.study.msm.utils.RandomUtil;
import com.gotostudy.study.msm.utils.TencentMsmKey;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 53Hertz
 **/

@Component
public class TencentMsmServiceImpl implements TencentMsmService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public R sendMsm(String phoneNum) {
        if(StringUtils.isEmpty(phoneNum)) {
            return R.error().message("手机号码不能为空");
        }

        String code = redisTemplate.opsForValue().get(phoneNum);
        //code为空
        if (StringUtils.isEmpty(code)) {
            code = RandomUtil.getSixBitRandom();
            //将生成的验证码存入到redis中
            redisTemplate.opsForValue().set(phoneNum, code,5, TimeUnit.MINUTES);
        }

        //短信模板中的两个参数变量
        String[] params = {code, "5"};


        try {
            /*
             * CAM 密钥查询：https://console.cloud.tencent.com/cam/capi
             */
            Credential cred = new Credential(TencentMsmKey.SECRET_ID, TencentMsmKey.SECRET_KEY);
            // 无特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();


            httpProfile.setReqMethod("POST");

            httpProfile.setConnTimeout(60);

            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();

            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            /* 实例化 SMS 的 client 对象
             * 第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量 */
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 您可以直接查询 SDK 源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用 IDE 进行开发，可以方便地跳转查阅各个接口和数据结构的文档说明 */
            com.tencentcloudapi.sms.v20190711.models.SendSmsRequest req = new SendSmsRequest();
            /* 填充请求参数，这里 request 对象的成员变量即对应接口的入参
             * 您可以通过官网接口文档或跳转到 request 对象的定义处查看请求参数的定义
             * 基本类型的设置:
             * 帮助链接：
             * 短信控制台：https://console.cloud.tencent.com/smsv2
             * sms helper：https://cloud.tencent.com/document/product/382/3773 */
            /* 短信应用 ID: 在 [短信控制台] 添加应用后生成的实际 SDKAppID，例如1400006666 */
            String sdkAppId = TencentMsmKey.APP_ID;
            req.setSmsSdkAppid(sdkAppId);
            /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，可登录 [短信控制台] 查看签名信息 */
            String sign = TencentMsmKey.SIGN_NAME;
            req.setSign(sign);
            /* 国际/港澳台短信 senderId: 国内短信填空，默认未开通，如需开通请联系 [sms helper] */
            String senderId = "";
            req.setSenderId(senderId);
            /* 模板 ID: 必须填写已审核通过的模板 ID，可登录 [短信控制台] 查看模板 ID */
            req.setTemplateID(TencentMsmKey.TEMPLATE_ID);
            /* 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号]
             * 例如+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号*/
            String[] phoneNumber = {"+86" + phoneNum + ""};
            req.setPhoneNumberSet(phoneNumber);
            /* 模板参数: 若无模板参数，则设置为空*/
            req.setTemplateParamSet(params);
            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = client.SendSms(req);
            // 输出 JSON 格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(res));
            // 可以取出单个值，您可以通过官网接口文档或跳转到 response 对象的定义处查看返回字段的定义
            System.out.println(res.getRequestId());
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return R.ok();
    }
}
