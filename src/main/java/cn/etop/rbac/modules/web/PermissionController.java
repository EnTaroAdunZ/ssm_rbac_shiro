package cn.etop.rbac.modules.web;

import cn.etop.rbac.common.util.PermissionUtil;
import cn.etop.rbac.modules.json.Msg;
import cn.etop.rbac.modules.json.PermissionJson;
import cn.etop.rbac.modules.model.*;
import cn.etop.rbac.modules.service.IPermissionService;
import cn.etop.rbac.modules.service.IUserService;
import cn.etop.rbac.modules.service.IRoleService;
import cn.etop.rbac.modules.service.IRoleToPermissionService;
import cn.etop.rbac.common.annotation.RequiredPermission;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @version V1.0
 * @Description: 拦截器类，实现权限加载拦截、登录拦截
 * @author: TingFeng Zhang
 * @date: 2017/7/14 14:22
 */

@Controller
@RequestMapping("/")
public class PermissionController{


    @Autowired
    IUserService userService;

    @Autowired
    IPermissionService permissionService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IRoleToPermissionService roleToPermissionService;


    @RequestMapping("permission/getPermissionByEdit")
    /**
    * @Description:通过ID得到权限[暂时没使用]
    * @param: [ID]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:Exception
    */
    public Msg getPermissionByID(@RequestParam(value = "ID")Long ID) throws Exception{
        Permission permission = permissionService.selectByPrimaryKey(ID);
        if(permission==null){
            Msg fail = Msg.fail();
            fail.setMsg("数据库错误，该权限不存在！");
            return fail;
        }
        else{
            return Msg.success().add("permission",permission);
        }

    }

    @RequiredPermission("权限添加")
    @RequiresPermissions("permission:permissionAdd")
    @RequestMapping("permission/permissionAdd")
    /**
    * @Description:权限添加
    * @param: [permission, pn, model]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView permissionAdd(Permission permission, @RequestParam(value = "pn",defaultValue ="1")Integer pn, Model model)  throws Exception{
        StringBuilder sb= new StringBuilder().append("com.test.");
        sb.append(permission.getExpression());
        permission.setExpression(sb.toString());
        permissionService.insert(permission);
        return new ModelAndView("redirect:/homePage/permissionManagement");
    }

    @RequiredPermission("权限编辑")
    @RequiresPermissions("permission:permissionEdit")
    @RequestMapping("permission/permissionEdit")
    /**
    * @Description:对传入的权限进行编辑
    * @param: [permission, pn, model, keyWord]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView permissionEdit(Permission permission,@RequestParam(value = "pn",defaultValue ="1")Integer pn
            , Model model,@RequestParam(value = "keyWord",defaultValue ="") String keyWord) throws Exception{
        StringBuilder sb= new StringBuilder().append("com.test.");
        sb.append(permission.getExpression());
        permission.setExpression(sb.toString());
        permissionService.updateByPrimaryKey(permission);
        ModelAndView modelAndView = new ModelAndView("redirect:/homePage/permissionManagement");
        modelAndView.addObject("keyWord",keyWord);
        modelAndView.addObject("pn",pn);
        return modelAndView;
    }


    @RequiredPermission("权限删除")
    @RequiresPermissions("permission:permissionDelete")
    @RequestMapping("permission/permissionDelete")
    /**
    * @Description:权限删除
    * @param: [ID, pn, model, keyWord]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView permissionDelete(@RequestParam(value = "ID",defaultValue ="0") Long ID,@RequestParam(value = "pn",defaultValue ="1")Integer pn
            , Model model,@RequestParam(value = "keyWord",defaultValue ="") String keyWord)  throws Exception{
        Permission permission = permissionService.selectByPrimaryKey(ID);
        if(permission.getName().equals("系统管理员")||permission.getName().equals("管理员")||permission.getName().equals("普通用户")){
            ModelAndView modelAndView = new ModelAndView("400");
            model.addAttribute("msg","无法删除！");
            return modelAndView;
        }
        permissionService.deleteByPrimaryKey(ID);
        ModelAndView modelAndView = new ModelAndView("redirect:/homePage/permissionManagement");
        modelAndView.addObject("keyWord",keyWord);
        modelAndView.addObject("pn",pn);
        return modelAndView;
    }



    @ResponseBody
    @RequestMapping("permission/permissionModel")
    /**
    * @Description:判断用户是否拥有该权限，以JSON方式返回
    * @param: [ID, httpServletRequest]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:Exception
    */
    public Msg permissionModel(@RequestParam(value = "ID",defaultValue ="0") Long ID,HttpServletRequest httpServletRequest)  throws Exception{
        HttpSession session =
                httpServletRequest.getSession();
        Long id = (Long) session.getAttribute("id");
        User user = userService.selectByPrimaryKey(id);
        HashSet<String> userPerssion= PermissionUtil.getPermissionWithUser(user,userService,roleService);
        Permission permission = permissionService.selectByPrimaryKey(ID);
        if(userPerssion.contains(permission.getExpression())){
            return Msg.success();
        }else{
            return Msg.fail();
        }


    }


    @ResponseBody
    @RequiredPermission("分配角色权限")
    @RequestMapping(value = "permission/updatePermissionJson", method = RequestMethod.POST)
    /**
    * @Description:为角色分配权限，以JSON方式返回
    * @param: [permissionList]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:Exception
    */
    public Msg updatePermissionJson(
                                    @RequestParam(value = "permissionList")List<Long> permissionList
    ) throws Exception{
        Long ID=permissionList.get(0);

        roleToPermissionService.deleteById(ID);
        boolean isBegin=true;
        for(Long id:permissionList){
            if(isBegin){
                isBegin=false;
                continue;
            }
            RoleToPermission roleToPermission=new RoleToPermission();
            Role role = new Role();
            role.setId(ID);
            Permission permission=new Permission();
            permission.setId(id);
            roleToPermission.setRole(role);
            roleToPermission.setPermission(permission);
            roleToPermissionService.addItem(roleToPermission);
        }
        return Msg.success();
    }


    @RequiredPermission("权限测试页面")
    @RequiresPermissions("permission:permissionTest")
    @RequestMapping("permission/permissionTest")
    /**
    * @Description:加载所有权限到权限测试页面上
    * @param:
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView permissionTest() throws Exception{
        ModelAndView mav = new ModelAndView("permissionTest");
        List<Permission> list = permissionService.list();
        mav.addObject("permission",list);
        return mav;
    }

    @ResponseBody
    @RequiredPermission("浏览角色所有权限")
    @RequestMapping("permission/getPermissionJson")
    /**
    * @Description:返回该角色所拥有所有权限，并返回JSON
    * @param: [ID]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:Exception
    */
    public Msg getPermissionJson(@RequestParam(value = "ID") Long ID) throws Exception{
        Throwable t = new Throwable();
        boolean isHasPermission= PermissionUtil.hasPermission(this.getClass(),t.getStackTrace()[0].getMethodName());
        if(!isHasPermission)
            return Msg.reject().add("returnMsg","你没有获得权限[用户添加]");
        List<PermissionJson> permissionJsonList=new ArrayList<>();
        List<Permission> list = permissionService.list();
        List<Role> roles = roleService.listPermission(ID);

        for(Permission p:list){
            PermissionJson permissionJson = new PermissionJson();
            permissionJson.setExit(false);
            permissionJson.setPermission(p);
            permissionJsonList.add(permissionJson);
        }

        for(Role role:roles){
            if(role!=null){
                List<Permission> permissionList = role.getPermissionList();
                if(permissionList!=null){
                    for(PermissionJson permissionJson:permissionJsonList){
                        String jsonName = permissionJson.getPermission().getName();
                        String permissionName = permissionList.get(0).getName();
                        if(jsonName.equals(permissionName)){

                            permissionJson.setExit(true);
                            }
                        }
                    }
                }
            }
        return Msg.success().add("permissionJsonList",permissionJsonList);
        }
    }




