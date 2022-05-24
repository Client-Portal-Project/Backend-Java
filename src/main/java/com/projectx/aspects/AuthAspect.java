package com.projectx.aspects;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.projectx.utility.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//will possibly get rid of this once auth0 is properly implemented
@Aspect
@Log4j2
@Component
public class AuthAspect {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Catches the method execution of a method within the UserController that does not have a
     * {@link com.projectx.aspects.annotations.NoAuth} annotation and sends back a ResponseEntity
     * depending on what's within the join point.
     *
     * @param pjp the ProceedingJoinPoint
     * @return a {@link ResponseEntity} that Unauthorized http status if the token within the join
     * point is invalid or not found, otherwise, proceed the join point if valid.
     */
    @Around("execution(* com.projectx.controllers.UserController.*(..))" +
            "&& !@annotation(com.projectx.aspects.annotations.NoAuth)")
    public ResponseEntity<?> authenticateToken(final ProceedingJoinPoint pjp) {
        final HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final String token = request.getHeader("authorization");
        ResponseEntity<?> response = null;
        if (token == null) {
            log.warn("No Authorization Token Received");
            response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            DecodedJWT decodedJWT = jwtUtil.verify(token);
            if (decodedJWT == null) {
                log.warn("Received Invalid Token");
                response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                log.info("Received Valid Token");
                request.setAttribute("userId", decodedJWT.getClaims().get("userId").asInt());
                try {
                    response = (ResponseEntity<?>) pjp.proceed();
                } catch (Throwable e) {
                    log.error("Unable to Proceed from Previous Join Point: "+e.getMessage());
                }
            }
        }
        return response;
    }
}
