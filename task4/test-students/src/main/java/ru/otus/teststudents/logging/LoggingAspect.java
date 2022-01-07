package ru.otus.teststudents.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("@annotation(ru.otus.teststudents.stereotype.LogEnable))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("---");
        System.out.println("Class: " + joinPoint.getTarget().getClass().getName());
        System.out.println("Proxy: " + joinPoint.getThis().getClass().getName());
        System.out.println("Call method: " + joinPoint.getSignature().getName());
        System.out.println("---");
    }

}
