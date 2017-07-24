package cn.etop.rbac.common.shiro.realms;


import cn.etop.rbac.common.util.PermissionUtil;
import cn.etop.rbac.login.service.IloginService;
import cn.etop.rbac.modules.model.Role;
import cn.etop.rbac.modules.model.User;
import cn.etop.rbac.modules.service.IRoleService;
import cn.etop.rbac.modules.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @version V1.0
 * @Description:
 * @author: TingFeng Zhang
 * @date: 2017/7/23 10:55
 */
@Controller
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    IloginService loginServiceImpl;

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();

        boolean isExit=false;
        try {
            isExit = userService.checkUserNameExit(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(isExit){
            throw new UnknownAccountException("用户不存在!");
        }
        User temp=null;
        try {
            temp=loginServiceImpl.getUserByAccount(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object principal = username;
        Object credentials = temp.getPassword();

        String realmName = getName();
        SimpleAuthenticationInfo info = null;
        info = new SimpleAuthenticationInfo(principal, credentials,  realmName);
        return info;

    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = (String)principalCollection.getPrimaryPrincipal();
        User user=null;
        try {
            user=loginServiceImpl.getUserByAccount(principal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Set<String> permission = new HashSet<>();
        try {
            permission= PermissionUtil.getPermissionWithUser(user,userService,roleService);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(String p:permission){
            info.addStringPermission(p);
        }
        return info;
    }
}
