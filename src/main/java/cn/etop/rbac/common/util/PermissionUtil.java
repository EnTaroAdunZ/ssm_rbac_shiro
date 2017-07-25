package cn.etop.rbac.common.util;

import cn.etop.rbac.modules.json.ZtreePermission;
import cn.etop.rbac.modules.model.Permission;
import cn.etop.rbac.common.annotation.RequiredPermission;
import cn.etop.rbac.modules.model.Role;
import cn.etop.rbac.modules.model.User;
import cn.etop.rbac.modules.service.IRoleService;
import cn.etop.rbac.modules.service.IRoleToPermissionService;
import cn.etop.rbac.modules.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * @version V1.0
 * @Description: 权限工具类
 * @author: TingFeng Zhang
 * @date: 2017/7/14 13:33
 */
@Component
public class PermissionUtil {

    public static boolean hasPermission(Class cur_class,String methodName) {

        Method[] methods = cur_class.getMethods();
        Method method = null;
        for (int i = 0; i < methods.length; i++) {
            if (methodName.equals(methods[i].getName())) {//和传入方法名匹配
                method = methods[i];
                break;
            }
        }

        Subject currentUser = SecurityUtils.getSubject();


        if (null != currentUser) {

            if (method.isAnnotationPresent(ResponseBody.class)) {
                if (method.isAnnotationPresent(RequiredPermission.class)) {
                    Permission permission = ajaxMethodToUrl(method);
                    boolean permitted = currentUser.isPermitted(permission.getExpression());
                    if (!permitted) {
                        return false;
                    }
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    /**
    * @Description:传入方法转换为权限名返回
    * @param: method
    * @returnType:Permission
    * @Exception:
    */
    public static Permission methodToPermission(Method method){
        StringBuilder sb=new StringBuilder(50)
                .append(method.getDeclaringClass().getCanonicalName())
                .append("."+method.getName());
        String value = method.getAnnotation(RequiredPermission.class).value();
        Permission permission = new Permission(sb.toString(),value);
        return permission;
    }


    /**
     * @Description:传入方法转换为Permission
     * @param: method
     * @returnType:Permission
     * @Exception:
     */
    public static Permission methodToUrl(Method method){
        String[] rm = method.getAnnotation(RequiresPermissions.class).value();
        String name = method.getAnnotation(RequiredPermission.class).value();
        Permission permission = new Permission(rm[0],name);
        return permission;
    }

    public static Permission ajaxMethodToUrl(Method method){
        String[] rm = method.getAnnotation(RequestMapping.class).value();
        String replace = rm[0].replace('/', ':');
        String name = method.getAnnotation(RequiredPermission.class).value();
        Permission permission = new Permission(replace+":ajax",name);
        return permission;
    }


    /**
     * 查询该角色所属所有的权限
     * @param id
     * @param roleService
     * @return
     * @throws Exception
     */
    public static List<Permission> getPermissionWithRole(Long id,IRoleService roleService) throws Exception {
        List<Role> roles = roleService.listPermission(id);
        List<Permission> permissions=new ArrayList<>();
        for(Role role :roles){
            if(role!=null)
           permissions.add( role.getPermissionList().get(0));
        }
        return permissions;
    }




    /**
     * @Description:传入User，返回该User所有权限
     * @param: method
     * @returnType:Permission
     * @Exception:
     */
    public static HashSet<String> getPermissionWithUser(User user, IUserService userService,IRoleService roleService) throws Exception {
        List<User> users = null;
        users = userService.listRoles(user.getId());
        HashSet<String> userPerssion = new HashSet<String>();
        if (users != null)
            for (User u : users) {
                if (u != null && u.getRoleList() != null) {
                    List<Role> roles = roleService.listPermission(u.getRoleList().get(0).getId());
                    if (roles != null) {
                        for (Role role : roles) {
                            if (role != null) {
                                String expression = role.getPermissionList().get(0).getExpression();
                                if (!userPerssion.contains(expression))
                                    userPerssion.add(expression);
                            }
                        }

                    }
                }
            }
        return userPerssion;
    }

}
