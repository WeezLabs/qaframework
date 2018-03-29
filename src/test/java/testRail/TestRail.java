package testRail;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD,ElementType.METHOD})
public @interface TestRail {
    int runId() default 0;
    int caseId() default 0;
    String defects() default "";
    String description() default "";
}
