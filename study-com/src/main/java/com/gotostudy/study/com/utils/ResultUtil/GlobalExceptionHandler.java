package com.gotostudy.study.com.utils.resultutil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 统一异常处理类
 * @author: 53Hertz
 * @create: 2021-03-09 16:27
 **/

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    //指定出现什么方法执行此方法Exception为最大的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("程序出错啦！呜呜呜！程序小哥正在努力抢修中！");
    }

    //指定出现什么方法执行此方法Exception为最大的异常
    // ArithmeticException特定异常处理,
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody//返回数据
    public R error(NullPointerException e) {
        e.printStackTrace();
        return R.error().message("出现空指针异常了！！！");
    }

    //自定义异常类
    @ExceptionHandler(GotostudyException.class)
    @ResponseBody//返回数据
    public R error(GotostudyException e) {
        e.printStackTrace();
        // 错误信息会写到日志文件中来
        log.error(e.getMessage());
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
