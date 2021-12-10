package com.schedule.proj.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodParamsResAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(MethodParamsRes)")
    public Object methodParamsRes(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();

        logger.info("{} with params {} resulted in {} ms", joinPoint.getSignature(),
                joinPoint.getArgs(), proceed);

        return proceed;
    }
}
