package com.gotostudy.study.com.utils.resultutil;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * * @description: 自定义异常类
 * @author: 53Hertz
 * @create: 2021-03-09 17:31
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GotostudyException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;//异常状态码

    private String msg;//异常状态信息

}
