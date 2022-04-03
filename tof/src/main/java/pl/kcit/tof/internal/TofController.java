package pl.kcit.tof.internal;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping
public @interface TofController {

    @AliasFor(annotation = RequestMapping.class, attribute = "consumes")
    String[] consumes() default "application/json";

    @AliasFor(annotation = RequestMapping.class, attribute = "produces")
    String[] produces() default "application/json";

    @AliasFor(annotation = RequestMapping.class)
    String path() default "";
}
