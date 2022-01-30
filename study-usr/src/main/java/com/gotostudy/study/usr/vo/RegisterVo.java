package com.gotostudy.study.usr.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: guli_parent
 * @description: 用于封装注册对象的信息实体类，包含验证码
 * @author: 53Hertz
 * @create: 2021-03-22 10:22
 **/

@Data
@ApiModel(value="注册对象", description="注册对象")
public class RegisterVo {

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "qq邮箱")
    private String email;

    @ApiModelProperty(value = "qq邮箱")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
