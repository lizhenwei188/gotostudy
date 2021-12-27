package com.gotostudy.study.edu.action;

import com.gotostudy.study.com.action.IPLog;
import com.gotostudy.study.com.annotation.LogAnnotation;
import com.gotostudy.study.com.constant.ActionDetail;
import com.gotostudy.study.com.constant.ActionResult;
import com.gotostudy.study.edu.entity.OperationLogEntity;
import com.gotostudy.study.edu.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class LogAopAction {

    @Autowired
    private OperationLogService operation;

    //切入点设置到自定义的log注解上
    @Pointcut("@annotation(com.gotostudy.study.com.annotation.LogAnnotation)")
    private void pointCutMethod() {
    }

    /**
     * 记录操作日志
     */
    OperationLogEntity operationLog = new OperationLogEntity();


    @AfterReturning("pointCutMethod()")  // 使用上面定义的切入点，操作后执行
    public void recordLog(JoinPoint joinPoint) {
        getLogComInfo(joinPoint);
        operationLog.setOperationResult(ActionResult.SUCCESS);
        // 报存记录
        operation.save(operationLog);
    }

    @AfterThrowing("pointCutMethod()") // 使用上面定义的切入点，出现异常执行
    public void exceptionLog(JoinPoint joinPoint) {
        getLogComInfo(joinPoint);
        operationLog.setOperationResult(ActionResult.ERROR);
        // 报存记录
        operation.save(operationLog);
    }


    public void getLogComInfo(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
//        User user = (User) request.getSession().getAttribute("user");
//        if (user == null) {
//            log.warn("user 信息为空");
//        } else {
//            // 设置用户信息
//            operationLog.setOperationName(user.getOperationName());
//            operationLog.setOperationAccount(user.getOperationAccount());
//        }



        operationLog.setOperationIp(IPLog.getClientIp(request));// 设置ip
        try {//下面开始获取 model，type
            Map<String, String> map = getLogMark(joinPoint);
            String[] split = map.get(IPLog.LOG_MODEL_TYPE).split("/");
            // 设置模块
            operationLog.setOperationModelOne(split[0]);
            operationLog.setOperationModelTwo(split[1]);
            // 设置类型
            operationLog.setOperationType(split[2]);
        } catch (ClassNotFoundException c) {
            log.error(c.getMessage());
        }
        HashMap<String, String> hashMap = ActionDetail.getActionDetails();
        // 下面开始获取 details;
        String logDetail = getLogDetail(hashMap, request);
        // 设置详情
        operationLog.setOperationDetail(logDetail);

    }

    // 获取 model，type
    private Map<String, String> getLogMark(JoinPoint joinPoint) throws ClassNotFoundException {
        Map<String, String> map = new HashMap<>();
        String methodName = joinPoint.getSignature().getName();//获取连接点的方法名字
        String targetName = joinPoint.getTarget().getClass().getName();//类全路径
        Class targetClass = Class.forName(targetName);//被增强的类
        Method[] methods = targetClass.getMethods();//被增强类中的所有方法
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                // 获取LogAnnotation注解
                LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
                map.put(IPLog.LOG_MODEL_TYPE, logAnnotation.actionInfo());
            }
        }
        return map;
    }

    private String getLogDetail(HashMap<String, String> hashMap, HttpServletRequest request) {
        // 获取得到方法的入参,因为可能存在同名参数所有为String[]
        Map<String, String[]> parameterMaps = request.getParameterMap();
        for (Map.Entry<String, String> infoMap : hashMap.entrySet()) {
            for (Map.Entry<String, String[]> parameterMap : parameterMaps.entrySet()) {
                if (infoMap.getKey().equals(parameterMap.getKey())) {
                    String[] split = infoMap.getValue().split("/");
                    if (split.length == 0) {
                        return infoMap.getValue();
                    } else if (split.length == 2) {
                        return split[0] + Arrays.toString(parameterMap.getValue()) + split[1];
                    } else if (split.length == 4) {
                        String description = split[0] + parameterMap.getValue()[0];

                        if (!parameterMap.getValue()[1].isEmpty()) {
                            description = description + split[1] + parameterMap.getValue()[1];
                        }
                        if (!parameterMap.getValue()[2].isEmpty()) {
                            description = description + split[2] + parameterMap.getValue()[2];
                        }
                        return description + split[3];
                    } else {
                        String description = split[0] + parameterMap.getValue()[0];

                        if (!parameterMap.getValue()[1].isEmpty()) {
                            description = description + split[1] + parameterMap.getValue()[1];
                        }
                        if (!parameterMap.getValue()[2].isEmpty()) {
                            description = description + split[2] + parameterMap.getValue()[2];
                        }
                        if (!parameterMap.getValue()[3].isEmpty()) {
                            description = description + split[3] + parameterMap.getValue()[3];
                        }
                        if (!parameterMap.getValue()[4].isEmpty()) {
                            description = description + split[4] + parameterMap.getValue()[4];
                        }
                        return description + split[5];
                    }
                }
            }
        }
        return null;
    }
}