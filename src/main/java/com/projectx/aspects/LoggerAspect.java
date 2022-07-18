package com.projectx.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Aspect
@Component
public class LoggerAspect {

    /**
     * Before a method is executed in the controllers package, message is printed
     * to show which method is being hit.
     *
     * @param joinPoint the joinPoint from the method execution
     */
    @Before("within(com.projectx.controllers.*)")
    public void logHit(final JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().toShortString() + " successfully hit.");
    }

    /**
     * after a controller method is executed, takes the response and join point to print a warning if
     * it's an error or info otherwise of the http status code and method.
     *
     * @param joinPoint a join point from the method execution
     * @param response a {@link ResponseEntity} being sent from the controller method
     */
    @AfterReturning(pointcut = "within(com.projectx.controllers.*)", returning = "response")
    public void log(final JoinPoint joinPoint, final ResponseEntity<?> response){
        final HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (response.getStatusCodeValue() >= 400) {
            if (log.isWarnEnabled()) {
                log.warn(joinPoint.getSignature().getDeclaringTypeName().split("\\.")[3] +
                        " resolved " + request.getMethod() +
                        " returning status code " + response.getStatusCode());
            }
        } else {
            if (log.isInfoEnabled()) {
                log.info(joinPoint.getSignature().getDeclaringTypeName().split("\\.")[3] +
                        " successfully resolved " + request.getMethod() +
                        " with status code " + response.getStatusCode());
            }
        }
    }
}
