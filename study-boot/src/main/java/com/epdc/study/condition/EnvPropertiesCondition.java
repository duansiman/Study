package com.epdc.study.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Objects;

public class EnvPropertiesCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        if (environment.containsProperty("env")) {
            String env = environment.getProperty("env");
            Map<String, Object> annotationAttributes = annotatedTypeMetadata.getAnnotationAttributes(Profile.class.getName());
            return Objects.nonNull(annotationAttributes)
                    && Objects.equals(((String[])annotationAttributes.get("value"))[0], env);
        }
        return false;
    }
}
