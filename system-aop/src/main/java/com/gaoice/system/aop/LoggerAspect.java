package com.gaoice.system.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 * 包含：
 * com.gaoice 包下 controller 的异常日志
 */
@Component
@Aspect
public class LoggerAspect {

    private static Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

    /**
     * com.gaoice 包下 @Controller 切入点
     */
    @Pointcut("execution(* com.gaoice..*.*Controller.*(..))")
    public void controllerPointcut() {
    }

    @AfterThrowing(value = "controllerPointcut()", throwing = "e")
    public void exceptionLog(JoinPoint joinPoint, Throwable e) {
        LOGGER.error(joinPoint.getSignature().toLongString(), e);
    }

}
