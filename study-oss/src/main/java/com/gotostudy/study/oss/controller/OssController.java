package com.gotostudy.study.oss.controller;


import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 53Hertz
 **/

//@CrossOrigin
@RestController
@RequestMapping("oss/file")
@Api(description="阿里云文件管理")
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping("/upload")           //得到需要上传的文件
    @ApiOperation(value = "文件上传")
    public R uploadOssFile(MultipartFile file) {
        //返回上传文件的路径
        String url = ossService.uploadFileAvatar(file);

        return R.ok().data("url",url);
    }
}
