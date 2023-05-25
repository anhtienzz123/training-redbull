package trainingredbull.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class PerformanceTrackingAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Around("trainingredbull.aop.CommonPointcutConfig.allPackageConfig()")
    //@Around("trainingredbull.aop.CommonPointcutConfig.trackTimeAnnotation()")
    public Object findExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("start - findExecutionTime");
        // start a timer
        long startTimeMillis = System.currentTimeMillis();
        
        // execute the method
        Object returnValue = proceedingJoinPoint.proceed();
        
        // stop the timer
        long stopTimeMillis = System.currentTimeMillis();
        
        long executionDuration = stopTimeMillis - startTimeMillis;
        
        logger.info("Around Aspect - {} method executed in {} ms", proceedingJoinPoint, executionDuration);
        
        logger.info("end - findExecutionTime");
        return returnValue;
    }
}
