package cn.etop.rbac.common.annotation;

import java.lang.annotation.*;


/**
 * @version V1.0
 * @Description: 权限标记类
 * @author: TingFeng Zhang
 * @date: 2017/7/14 16:32
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredPermission {
    String value();
}
