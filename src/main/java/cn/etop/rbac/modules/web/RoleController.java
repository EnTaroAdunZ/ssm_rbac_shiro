package cn.etop.rbac.modules.web;

import cn.etop.rbac.common.util.PermissionUtil;
import cn.etop.rbac.modules.json.Msg;
import cn.etop.rbac.modules.json.RoleJson;
import cn.etop.rbac.modules.model.*;
import cn.etop.rbac.modules.service.IUserService;
import cn.etop.rbac.modules.service.IRoleService;
import cn.etop.rbac.modules.service.IUserToRoleService;
import cn.etop.rbac.common.annotation.RequiredPermission;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Description: 拦截器类，实现权限加载拦截、登录拦截
 * @author: TingFeng Zhang
 * @date: 2017/7/14 14:22
 */
@Controller
@RequestMapping("/")

public class RoleController {

    @Autowired
    IRoleService roleService;

    @Autowired
    IUserService userService;

    @Autowired
    IUserToRoleService userToRoleService;

    @RequiredPermission("权限浏览")
    @RequiresPermissions("role:permissionBrowse")
    @RequestMapping("role/permissionBrowse")
    /**
    * @Description:浏览角色下所有权限[暂时没有使用]
    * @param: [pn, wid, model]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView permissionBrowse(@RequestParam(value = "pn",defaultValue ="1")Integer pn,
                                         @RequestParam(value = "wid",defaultValue ="0")Long wid,
                                         Model model) throws Exception{
        ModelAndView mav = new ModelAndView("useless/permissionBrowse");
        PageHelper.startPage(pn, 10);
        List<Role> roles = roleService.listPermission(wid);
        PageInfo pageInfo=new PageInfo(roles);
        model.addAttribute("pageInfo",pageInfo);
        return mav;
    }


    @ResponseBody
    @RequestMapping("role/getRoleById")
    /**
    * @Description:通过ID得到角色,返回JSON数据
    * @param: [ID]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:Exception
    */
    public Msg getRoleById(@RequestParam(value = "ID")Long ID)  throws Exception{
        Role role = roleService.selectByPrimaryKey(ID);
        if(role!=null){
            return Msg.success().add("role",role);
        }
        else{
            return Msg.fail().add("errorMsg","数据库错误，该角色不存在！");
        }
    }


    @RequiredPermission("角色添加")
    @RequiresPermissions("role:roleAdd")
    @RequestMapping("role/roleAdd")
    /**
    * @Description:角色添加
    * @param: [role]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView roleAdd(@Valid Role role)  throws Exception{
        roleService.insert(role);
        return new ModelAndView("redirect:/homePage/roleManagement");
    }


    @RequiredPermission("角色编辑")
    @RequiresPermissions("role:roleEdit")
    @RequestMapping("role/roleEdit")
    /**
    * @Description:角色编辑
    * @param: [role, pn, wid, model, keyWord]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView roleEdit(@Valid Role role,
            @RequestParam(value = "pn",defaultValue ="1")Integer pn,
                                 @RequestParam(value = "wid",defaultValue ="0")Long wid,
                                 Model model,@RequestParam(value = "keyWord",defaultValue ="")String keyWord) throws Exception{
        roleService.updateByPrimaryKey(role);
        ModelAndView modelAndView = new ModelAndView("redirect:/homePage/roleManagement");
        modelAndView.addObject("keyWord",keyWord);
        modelAndView.addObject("pn",pn);
        return modelAndView;

    }

    @RequiredPermission("角色删除")
    @RequiresPermissions("role:roleDelete")
    @RequestMapping("role/roleDelete")
    /**
    * @Description:角色删除
    * @param: [ID, pn, wid, model, keyWord]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView roleDelete(@RequestParam(value = "ID",defaultValue ="0") Long ID, @RequestParam(value = "pn",defaultValue ="1")Integer pn,
                                   @RequestParam(value = "wid",defaultValue ="0")Long wid,
                                   Model model,@RequestParam(value = "keyWord",defaultValue ="")String keyWord) throws Exception{
        roleService.deleteByPrimaryKey(ID);
        ModelAndView modelAndView = new ModelAndView("redirect:/homePage/roleManagement");
        modelAndView.addObject("keyWord",keyWord);
        modelAndView.addObject("pn",pn);
        return modelAndView;
    }


    @RequiredPermission("权限分配")
    @RequiresPermissions("role:roleHandlePermission")
    @RequestMapping("role/roleHandlePermission")
    /**
    * @Description:权限分配
    * @param:
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView roleHandlePermission() throws Exception{

        return new ModelAndView("redirect:/homePage/roleManagement");
    }

    @ResponseBody
    @RequiredPermission("分配用户角色")
    @RequestMapping(value = "role/updateRoleJson", method = RequestMethod.GET)
    /**
    * @Description:
    * @param: [roleList]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:Exception
    */
    public Msg updateRoleJson (
            @RequestParam(value = "roleList")List<Long> roleList
    ) throws Exception{
        Throwable t = new Throwable();
        boolean isHasPermission=PermissionUtil.hasPermission(this.getClass(),t.getStackTrace()[0].getMethodName());
        if(!isHasPermission)
            return Msg.noPermission().add("returnMsg","您没有权限【分配用户角色】");
        Long ID=roleList.get(0);

        userToRoleService.deleteById(ID);
        boolean isBegin=true;
        for(Long id:roleList){
            if(isBegin){
                isBegin=false;
                continue;
            }
            UserToRole userToRole = new UserToRole();
            Role role = new Role();
            role.setId(id);
            User user=new User();
            user.setId(ID);
            userToRole.setRole(role);
            userToRole.setUser(user);
            userToRoleService.addItem(userToRole);
        }

            return Msg.success();


    }

    @ResponseBody
    @RequiredPermission("浏览用户所有角色")
    @RequestMapping("role/getRoleJson")
    /**
    * @Description:返回某用户所拥有的所有权限,以JSON的方式
    * @param: [ID]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:Exception
    */
    public Msg getRoleJson(@RequestParam(value = "ID") Long ID) throws Exception{
        Throwable t = new Throwable();
        boolean isHasPermission=PermissionUtil.hasPermission(this.getClass(),t.getStackTrace()[0].getMethodName());
        if(!isHasPermission)
            return Msg.noPermission().add("returnMsg","您没有权限【浏览用户所有角色】");
        List<RoleJson> roleJsons=new ArrayList<>();
        List<Role> list = roleService.getRoles();
        List<User> users = userService.listRoles(ID);
        for(Role r:list){
            RoleJson roleJson = new RoleJson();
            roleJson.setExit(false);
            roleJson.setRole(r);
            roleJsons.add(roleJson);
        }

        for(User user:users){
            if(user!=null){
                List<Role> roles = user.getRoleList();
                if(roles!=null){
                    for(RoleJson roleJson:roleJsons){
                        String jsonName = roleJson.getRole().getName();
                        String permissionName = roles.get(0).getName();
                        if(jsonName.equals(permissionName)){

                            roleJson.setExit(true);
                        }

                    }
                }
            }
        }
        return Msg.success().add("roleJsons",roleJsons);

    }

}
