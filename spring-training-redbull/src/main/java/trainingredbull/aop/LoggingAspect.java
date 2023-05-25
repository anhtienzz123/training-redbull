package trainingredbull.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before("trainingredbull.aop.CommonPointcutConfig.allPackageConfig()")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        logger.info("IN - {} - {}", joinPoint, joinPoint.getArgs());
    }

    @After("trainingredbull.aop.CommonPointcutConfig.allPackageConfig()")
    public void logAfterMethodCall(JoinPoint joinPoint) {
        logger.info("OUT - {}", joinPoint);
    }

    @AfterThrowing(pointcut = "trainingredbull.aop.CommonPointcutConfig.allPackageConfig()", throwing = "exception")
    public void logMethodCallAfterException(JoinPoint joinPoint, Exception exception) {
        logger.info("AfterThrowing Aspect - {} has throw an exception {}", joinPoint, exception);
    }
    
    @AfterReturning(pointcut = "trainingredbull.aop.CommonPointcutConfig.allPackageConfig()", returning = "resultValue")
    public void logMethodCallAfterSuccessfulExecution(JoinPoint joinPoint, Object resultValue) {
        logger.info("AfterReturning Aspect - {} has returned {}", joinPoint, resultValue);
    }
}
