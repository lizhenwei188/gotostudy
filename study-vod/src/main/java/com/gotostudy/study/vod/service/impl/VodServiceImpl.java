package com.gotostudy.study.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.gotostudy.study.com.utils.resultutil.GotostudyException;
import com.gotostudy.study.vod.service.VodService;
import com.gotostudy.study.vod.utils.ConstantsVodUtils;
import com.gotostudy.study.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @description:
 * @author: 53Hertz
 **/

@Service
public class VodServiceImpl implements VodService {

    //上传视频到阿里云
    @Override
    public String uploadAliYunVideo(MultipartFile file) {
        if (file != null) {

            //表示取到本地文件的名称，带有后缀名称
            String fileName = file.getOriginalFilename();

            //表示上传以后文件的名称和实际文件的名称一致且去除掉后缀名称
            assert fileName != null;
            String title = fileName.substring(0, fileName.lastIndexOf("."));

            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }


            UploadStreamRequest request = new UploadStreamRequest(ConstantsVodUtils.KEY_ID, ConstantsVodUtils.KEY_SECRET, title, fileName, inputStream);
            /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
            //request.setShowWaterMark(true);
            /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
            //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
            /* 视频分类ID(可选) */
            //request.setCateId(0);
            /* 视频标签,多个用逗号分隔(可选) */
            //request.setTags("标签1,标签2");
            /* 视频描述(可选) */
            //request.setDescription("视频描述");
            /* 封面图片(可选) */
            //request.setCoverURL("http://cover.sample.com/sample.jpg");
            /* 模板组ID(可选) */
            //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
            /* 工作流ID(可选) */
            //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
            /* 存储区域(可选) */
            //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
            /* 开启默认上传进度回调 */
            // request.setPrintProgress(true);
            /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
            // request.setProgressListener(new PutObjectProgressListener());
            /* 设置应用ID*/
            //request.setAppId("app-1000000");
            /* 点播服务接入点 */
            //request.setApiRegionId("cn-shanghai");
            /* ECS部署区域*/
            // request.setEcsRegionId("cn-shanghai");
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return response.getVideoId();
        } else {
            throw new GotostudyException(20001,"视频为空，请重新上传视频");
        }

    }

    @Override//实现删除多个阿里云视频操作
    public void removeMultiVideo(List<String> videoList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantsVodUtils.KEY_ID, ConstantsVodUtils.KEY_SECRET);

            //创建一个删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //向request中设置要删除视频的id值
            //多个id之间用,隔开 1,2,3...需要将videoList转化为这种形式
            //可以遍历，中间加入,但麻烦一些，可以用个工具类
            //org.apache.commons.lang.StringUtils用这个里面的StringUtils
            //和对象转换用的不是同一个        videoList.toArray()把集合变成数组，变不变都可
            String videoId = StringUtils.join(videoList.toArray(), ",");
            request.setVideoIds(videoId);

            //调用初始化的对象，实现删除
            client.getAcsResponse(request);
//            return R.ok();
        } catch (ClientException | com.netflix.client.ClientException e) {
            e.printStackTrace();
            throw new GotostudyException(20001, "程序出错啦，删除视频失败");
        }
    }

    @Override//删除单个的阿里云视频
    public void removeVideo(String videoId) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantsVodUtils.KEY_ID, ConstantsVodUtils.KEY_SECRET);

            //创建一个删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //向request中设置要删除视频的id值
            request.setVideoIds(videoId);

            //调用初始化的对象，实现删除
            client.getAcsResponse(request);
        } catch (ClientException | com.netflix.client.ClientException e) {
            e.printStackTrace();
            throw new GotostudyException(20001, "程序出错啦，删除视频失败");
        }
    }
}
