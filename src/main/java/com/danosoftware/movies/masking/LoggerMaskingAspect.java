package com.danosoftware.movies.masking;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

@Aspect
@Component
public class LoggerMaskingAspect {

    public LoggerMaskingAspect() {
        System.out.println("init");
    }

//    @Around("execution(* com.danosoftware.movies.masking.*.*(..))")
//    public Object maskSensitiveDataInLogs22(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object[] args = joinPoint.getArgs();
//        return joinPoint.proceed();
//    }

    @Around("execution(static * org.slf4j.Logger..*.*(..))")
    public Object maskSensitiveDataInLogs23(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        return joinPoint.proceed();
    }

    @Before("@annotation(sensitive)")
    public void sens(
            JoinPoint joinPoint,
            Sensitive sensitive) {
        System.out.print("hello");
    }

    @Around("execution(* org.slf4j.Logger.info(..)) || " +
            "execution(* org.slf4j.Logger.debug(..)) || " +
            "execution(* org.slf4j.Logger.warn(..)) || " +
            "execution(* org.slf4j.Logger.error(..))")
    public Object maskSensitiveDataInLogs(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        if (args != null && args.length > 0) {
            Object[] maskedArgs = Arrays.stream(args)
                    .map(arg -> {
                        if (isMaskable(arg)) {
                            return SensitiveDataMasker.maskSensitiveFields(arg);
                        }
                        return arg;
                    })
                    .toArray();

            return joinPoint.proceed(maskedArgs);
        }

        return joinPoint.proceed();
    }

    private boolean isMaskable(Object obj) {
        if (obj == null) return false;

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Sensitive.class)) {
                return true;
            }
        }
        return false;
    }
}

