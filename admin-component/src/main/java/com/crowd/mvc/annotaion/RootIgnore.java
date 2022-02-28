package com.crowd.mvc.annotaion;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)//只能标注到类上
public @interface RootIgnore {
    /*
    * 说明：标记在类上，Spring Root容器在
    * 扫描包的时候将会忽略掉当前类对象
    * */
}
