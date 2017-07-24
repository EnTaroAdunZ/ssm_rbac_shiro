package cn.etop.rbac.common.shiro.factory;

import cn.etop.rbac.common.annotation.RequiredPermission;
import cn.etop.rbac.common.util.AopTargetUtils;
import cn.etop.rbac.common.util.PermissionUtil;
import cn.etop.rbac.modules.mapper.PermissionMapper;
import cn.etop.rbac.modules.model.Permission;
import cn.etop.rbac.modules.model.Role;
import cn.etop.rbac.modules.model.RoleToPermission;
import cn.etop.rbac.modules.service.IPermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Description:
 * @author: TingFeng Zhang
 * @date: 2017/7/23 17:39
 */
@Service
public class FilterChainDefinitionMapBuilder implements ApplicationContextAware{
    @Autowired
    IPermissionService permissionService;

    @Autowired
    private ApplicationContext ctx;

//    @Autowired
//    LinkedHashMap<String, String> map;
//
//    public LinkedHashMap<String, String> buildFilterChainDefinitionMap() throws Exception {
//         这里从数据库拿权限数据
//         map.put("/login", "anon");
//        map.put("/checkUser", "anon");
//        map.put("/**", "authc");
//        return map;
//    }


    /**
     * @Description:扫描带RequiredPermission注解的方法,并刷新权限保存到数据库
     * @param:
     * @returnType:void
     * @Exception:
     */
    public void reload() throws Exception {
        List<Permission> permissions = permissionService.list();
        Map<String, Object> beansWithAnnotation = ctx.getBeansWithAnnotation(Controller.class);
        for(Map.Entry<String,Object> entry:beansWithAnnotation.entrySet()){
            Object target = AopTargetUtils.getTarget(entry.getValue());
            Method[] declaredMethods = target.getClass().getDeclaredMethods();
            for(Method m:declaredMethods){
                Annotation[] declaredAnnotations = m.getDeclaredAnnotations();
                if(m.isAnnotationPresent(RequestMapping.class)){
                    if(m.isAnnotationPresent(RequiredPermission.class)){
                        Permission permission=null;
                        if(m.isAnnotationPresent(RequiresPermissions.class)){
                            permission = PermissionUtil.methodToUrl(m);
                        }else if(m.isAnnotationPresent(ResponseBody.class)){
                            permission = PermissionUtil.ajaxMethodToUrl(m);
                        }
//                        String[] value = m.getAnnotation(RequestMapping.class).value();
//                          map.put('/'+value[0],"perms["+permission.getExpression()+"]");
                        if(!permissions.contains(permission)){
                            permissionService.save(permission);
                        }

                    }
//                    else{
//                        String[] value = m.getAnnotation(RequestMapping.class).value();
//                        map.put('/'+value[0],"anon");
//                    }
                }

            }
        }
    }



    @Override
    /**
    * @Description:容器启动后初始化权限
    * @param: [applicationContext]
    * @returnType:void
    * @Exception:
    */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx=applicationContext;
        try {
            reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
