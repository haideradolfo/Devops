package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j

public class AspectClass {
    @Before("execution(* tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services..*.*(..))")
    public void logMethodEntry(JoinPoint jp){
        log.info("In method X"+ jp.getSignature().getName());
    }

    @After("execution(* tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services..*.*(..))")
    public void logMethodEnd(JoinPoint jp){
        log.info("Out of method X"+ jp.getSignature().getName());
    }


}
