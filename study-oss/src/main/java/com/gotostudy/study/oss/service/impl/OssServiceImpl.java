package com.gotostudy.study.oss.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.gotostudy.study.oss.service.OssService;
import com.gotostudy.study.oss.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.joda.time.DateTime;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @description:
 * @author: 53Hertz
 **/

@Component
public class OssServiceImpl implements OssService {

    @Override//上传头像到oss
    public String uploadFileAvatar(MultipartFile file) {
        //获取到站点
        String endPoint = ConstantPropertiesUtils.END_POINT;

        //获取到bucket的名称
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        //获取到密钥和名称
        String keyId = ConstantPropertiesUtils.KEY_ID;
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;

        //创建oss实例
        OSS build = new OSSClientBuilder().build(endPoint, keyId, keySecret);

        //获取文件的名称
        String filename = file.getOriginalFilename();

        //在每个文件的后面添加一个随机唯一的值，让每个文件的名称不相同，
        //防止后者把前者覆盖掉                     把生成的uuid中的-换成空字符串，换不换都可以
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        filename = uuid + filename;//要在前面加入，后面有后缀名，不能在后面加入

        //可以将文件进行分类存储，例如按照日期进行分类
        //import org.joda.time.DateTime;用这个依赖获取到的
        String date = new DateTime().toString("yyyy/MM/dd");
        filename = "gotostudy" + "/" + "avatar" + "/" + date + "/" + filename;
        try {//文件输入流
            InputStream inputStream = file.getInputStream();

            //第一个参数：bucket名称
            //第二个参数：上传到oss的路径或名称 例如：aa/bb/1.jpj
            //第三个参数：输入文件流
            build.putObject(bucketName,filename , inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            build.shutdown();
        }
        //把上传文件的路径进行返回，手动拼接
        return "http://" + bucketName + "." + endPoint + "/" + filename;
    }
}
