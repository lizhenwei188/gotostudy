/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.gotostudy.study.com.utils.ResultUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共返回类
 */
@Data
public class R {
	@ApiModelProperty(value = "是否成功")
	private Boolean success;

	@ApiModelProperty(value = "返回码")
	private Integer code;

	@ApiModelProperty(value = "返回消息")
	private String message;

	@ApiModelProperty(value = "返回数据")
	private Map<String, Object> data = new HashMap<>();

	//构造方法私有化，不能直接new对象，使用下面的static方法调用生成对象
	private R(){}

	//成功的静态方法
	public static R ok() {
		R r = new R();//自己可以在本类中new 不能在其他类中new
		r.setSuccess(true);
		r.setMessage("成功了哈哈哈哈哈啊哈哈哈哈哈哈");
		r.setCode(ResultCode.SUCCESS);
		return r;
	}

	//失败的静态方法
	public static R error() {
		R r = new R();//自己可以在本类中new 不能在其他类中new
		r.setSuccess(false);
		r.setMessage("失败了呜呜呜呜呜呜呜呜呜呜呜呜");
		r.setCode(ResultCode.ERROR);
		return r;
	}




	//下面的代码可以做链式编程，返回的值都为对象，所以可以一直...方法
	public R data(String key, Object value){
		this.data.put(key, value);
		return this;
	}

	public R data(Map<String, Object> map){
		this.setData(map);
		return this;
	}

	public R success(Boolean success){
		this.setSuccess(success);
		return this;
	}

	public R message(String message){
		this.setMessage(message);
		return this;
	}

	public R code(Integer code){
		this.setCode(code);
		return this;
	}

}
