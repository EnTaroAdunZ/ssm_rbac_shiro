package cn.etop.rbac.login.web;

import cn.etop.rbac.modules.model.Permission;
import cn.etop.rbac.modules.model.User;
import cn.etop.rbac.modules.service.IPermissionService;
import cn.etop.rbac.modules.service.IUserService;
import cn.etop.rbac.modules.model.Role;
import cn.etop.rbac.modules.service.IRoleService;
import cn.etop.rbac.common.annotation.RequiredPermission;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * @version V1.0
 * @Description: 处理个人主页
 * @author: TingFeng Zhang
 * @date: 2017/7/14 13:44
 */
@Controller
@RequestMapping("/")
public class HomePageController{

    @Autowired
    IUserService userService;

    @Autowired
    IPermissionService permissionService;

    @Autowired
    IRoleService roleService;

    @RequiredPermission("用户查询")
    @RequiresPermissions("homePage:userManagement")
    @RequestMapping("homePage/userManagement")
    /**
    * @Description:进入用户管理页面
    * @param: [pn, model]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView userManagement(@RequestParam(value = "pn",defaultValue ="1")Integer pn, Model model) throws Exception{
        PageHelper.startPage(pn, 10);
        ModelAndView mav = new ModelAndView("userManagement");

        List<User> users = userService.getUsers();
        PageInfo pageInfo=new PageInfo(users);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pn",pn);
        return mav;
    }

    @RequiredPermission("权限查询")
    @RequiresPermissions("homePage:permissionManagement")
    @RequestMapping("homePage/permissionManagement")
    /**
    * @Description:进入权限查询页面
    * @param: [pn, model, keyWord]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView permissionManagement(@RequestParam(value = "pn",defaultValue ="1")Integer pn
            , Model model,@RequestParam(value = "keyWord",defaultValue ="") String keyWord)  throws Exception{
        ModelAndView mav = new ModelAndView("permissionManagement");
        PageHelper.startPage(pn, 10);
        List<Permission> permissions = permissionService.selectByKeyWord(keyWord);
        PageInfo pageInfo=new PageInfo(permissions);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("keyWord",keyWord);
        return mav;
    }

    @RequiredPermission("角色查询")
    @RequiresPermissions("homePage:roleManagement")
    @RequestMapping("homePage/roleManagement")
    /**
    * @Description:进入角色查询界面
    * @param: [pn, model, keyWord]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:
    */
    public ModelAndView roleManagement(@RequestParam(value = "pn",defaultValue ="1")Integer pn
            , Model model,@RequestParam(value = "keyWord",defaultValue ="")String keyWord)  throws Exception{
        ModelAndView mav = new ModelAndView("roleManagement");
        PageHelper.startPage(pn, 10);
        List<Role> roles = roleService.selectByKeyWord(keyWord);
        PageInfo pageInfo=new PageInfo(roles);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("keyWord",keyWord);
        return mav;
    }


    @RequestMapping("homePage/exit")
    /**
    * @Description:退出登录
    * @param: [request]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:
    */
    public ModelAndView roleManagement(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("index");
        Subject currentUser = SecurityUtils.getSubject();
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        if (currentUser.isAuthenticated()) {
            currentUser.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        }
        return mav;
    }


    @RequestMapping("homePage/treePermission")
    public ModelAndView treePermission(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("useless/treePermission");
        return mav;
    }

}
