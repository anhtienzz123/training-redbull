package trainingredbull.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcutConfig {

    @Pointcut("execution(* trainingredbull..*.*(..))")
    public void allPackageConfig() {}

    @Pointcut("bean(*Service*)")
    public void dataPackageConfigUsingBean() {}

    @Pointcut("@annotation(trainingredbull.annotation.TrackTime)")
    public void trackTimeAnnotation() {}
}
