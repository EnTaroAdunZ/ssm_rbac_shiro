package cn.etop.rbac.login.web;

import cn.etop.rbac.modules.json.Msg;
import cn.etop.rbac.modules.model.User;
import cn.etop.rbac.modules.service.IPermissionService;
import cn.etop.rbac.modules.service.IUserService;
import cn.etop.rbac.login.service.IloginService;
import cn.etop.rbac.modules.web.PermissionController;
import cn.etop.rbac.modules.model.Role;
import cn.etop.rbac.modules.service.IRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by 63574 on 2017/7/13.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    IPermissionService permissionService;

    @Autowired
    IloginService loginServiceImpl;

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;



    @RequestMapping("returnWrong")
    /**
    * @Description:返回错误页面
    * @param: [msg]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:
    */
    public ModelAndView returnWrong(String msg) {
        ModelAndView mav = new ModelAndView("400");
        mav.addObject("msg", msg);
        return mav;
    }

    @ResponseBody
    @RequestMapping("checkUser")
    /**
    * @Description:检查是否可以登录
    * @param: [username, password]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:
    */
    public Msg checkUser(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password,HttpServletRequest httpServletRequest) throws  Exception{
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            // rememberme
            token.setRememberMe(true);
            try {
                // 执行登录.
                currentUser.login(token);
            }
            catch (IncorrectCredentialsException e){
                return Msg.fail().add("msg","帐号密码错误");
            }
            catch (AuthenticationException ae) {
                return Msg.fail().add("msg",ae.getMessage());
            }
        }
        User user = new User();
        user.setAccount(username);
        user.setPassword(password);
        User temp = loginServiceImpl.login(user);
        ModelAndView mav = new ModelAndView("personalHomepage");
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("id");
        session.setAttribute("id", temp.getId());
        if (temp == null) {
            return Msg.fail();
        } else {
            return Msg.success();
        }
    }

    @RequestMapping("login")
    /**
    * @Description:返回登录界面
    * @param: []
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:
    */
    public ModelAndView handleEnter() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @RequestMapping("notpermission")
    /**
    * @Description:没有权限时返回
    * @param: []
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:
    */
    public ModelAndView handleNotpermission() {
        ModelAndView mav = new ModelAndView("notpermission");
        return mav;
    }

    @RequiresAuthentication
    @RequestMapping("user/index")
    public ModelAndView handleLogin(HttpServletRequest httpServletRequest) throws Exception{
//        Subject currentUser = SecurityUtils.getSubject();
//
//        if (!currentUser.isAuthenticated()) {
//            // 把用户名和密码封装为 UsernamePasswordToken 对象
//            UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword());
//            // rememberme
//            token.setRememberMe(true);
//            try {
//                // 执行登录.
//                currentUser.login(token);
//            }
//            catch (IncorrectCredentialsException e){
//                ModelAndView mav = new ModelAndView("400");
//                mav.addObject("msg","帐号密码错误");
//                return mav;
//            }
//            catch (AuthenticationException ae) {
//                ModelAndView mav = new ModelAndView("400");
//                mav.addObject("msg",ae.getMessage());
//                return mav;
//            }
//        }

        ModelAndView mav = new ModelAndView("personalHomepage");
        HttpSession session = httpServletRequest.getSession();
        Long id = (Long) session.getAttribute("id");
        User user = userService.selectByPrimaryKey(id);
        mav.addObject("user", user);
        return mav;
    }

//不带shiro的登录控制器
//    @RequestMapping("/user/index")
//    /**
//    * @Description:进入个人主页
//    * @param: [user, httpServletRequest]
//    * @returnType:org.springframework.web.servlet.ModelAndView
//    * @Exception:
//    */
//    public ModelAndView handleLogin(User user, HttpServletRequest httpServletRequest) throws Exception{
////        if (result.hasErrors()) {
////            List<FieldError> fieldErrors = result.getFieldErrors();
////            StringBuilder stringBuilder = new StringBuilder();
////            for (FieldError fieldError1 : fieldErrors) {
////                stringBuilder.append(fieldError1.getDefaultMessage()+"\n");
////            }
////
////        }
//        HttpSession session =
//                httpServletRequest.getSession();
//        Long id = (Long) session.getAttribute("id");
//        User temp = loginServiceImpl.login(user);
//        if (user.getAccount() == null || user.getPassword() == null) {
//            //从别的界面来
//            if (id == null) {
//                //没有登陆过
//                ModelAndView mav = new ModelAndView("index");
//                return mav;
//            } else {
//                //登录过加载原来的数据
//                ModelAndView mav = new ModelAndView("personalHomepage");
//                temp = userService.selectByPrimaryKey(id);
//                mav.addObject("user", temp);
//
//                return mav;
//            }
//        } else {
//            //从登录界面来
//            if (temp == null) {
//                //帐号不存在
//                ModelAndView mav = new ModelAndView("400");
//                mav.addObject("msg","帐号不存在！");
//                return mav;
//            } else {
//                //更新权限
//                Pattern reg = Pattern.compile("^[a-z0-9_-]{3,16}$");
//                Matcher accMat = reg.matcher(user.getAccount());
//                boolean accMatRe = accMat.matches();
//                Matcher passMat = reg.matcher(user.getPassword());
//                boolean passMatRe = passMat.matches();
//                if(accMatRe==false||passMatRe==false){
//                    ModelAndView mav = new ModelAndView("400");
//                    mav.addObject("msg", "帐号密码必须是3-16位英文和数字组合");
//                    return mav;
//                }
//                session.removeAttribute("id");
//                session.removeAttribute("userPermission");
//                List<User> users = userService.listRoles(temp.getId());
//                HashSet<String> userPerssion = new HashSet<String>();
//                if (users != null)
//                    for (User u : users) {
//                        if (u != null && u.getRoleList() != null) {
//                            List<Role> roles = roleService.listPermission(u.getRoleList().get(0).getId());
//                            if (roles != null) {
//                                for (Role role : roles) {
//                                    if (role != null) {
//                                        String expression = role.getPermissionList().get(0).getExpression();
//                                        if (!userPerssion.contains(expression))
//                                            userPerssion.add(expression);
//                                    }
//                                }
//
//                            }
//                        }
//                    }
//                session.setAttribute("id", temp.getId());
//                session.setAttribute("userPermission", userPerssion);
//                ModelAndView mav = new ModelAndView("personalHomepage");
//                mav.addObject("user", temp);
//                mav.addObject("userPermission", userPerssion);
//                return mav;
//            }
//        }
//    }


}
