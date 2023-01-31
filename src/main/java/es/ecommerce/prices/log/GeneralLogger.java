package es.ecommerce.prices.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Log a message before and after the invocation of method annotated with {@link Logged}.</p>
 * <p>The logged message has the format [Invoking|Returning] - signatureMethod(Args...). For instance "Invoking - x.y.method(arg)"</p>
 *
 * <p>Exception are logged too.
 */
@Aspect
@Component
@Slf4j
public class GeneralLogger {
    private String getMethodFullSignature(JoinPoint joinPoint) {
        return String.format("%s.%s(%s)",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Before("@annotation(logged)")
    public void logBefore(JoinPoint joinPoint, Logged logged) {
        log.info(String.format("Invoking - %s", getMethodFullSignature(joinPoint)));
    }

    @AfterReturning(value = "@annotation(logged)", returning = "returnedObject")
    public void logAfter(JoinPoint joinPoint, Logged logged, Object returnedObject) {
        log.info(String.format("Returning - %s. Return = %s",
                getMethodFullSignature(joinPoint),
                Optional.ofNullable(returnedObject).orElse("Void")));
    }

    @AfterThrowing(value = "@annotation(logged)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Logged logged, Exception ex) {
        log.warn(String.format("Exception thrown - %s. Message = %s", getMethodFullSignature(joinPoint), ex.getMessage()));
    }
}
