package com.example.testdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Created by 楚天佳
 * @date 2019/2/8 23:26
 */
/*面向切面AOP*/
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.example.testdemo.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {/*执行方法前调用*/
        StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {/*整个参数信息打印出来*/
            sb.append("arg:" + arg.toString() + "|");
        }
        logger.info("before method:" + sb.toString());
    }

    @After("execution(* com.example.testdemo.controller.IndexController.*(..))")
    public void afterMethod() {/*执行方法后调用*/
        logger.info("after method" + new Date());
    }
}
