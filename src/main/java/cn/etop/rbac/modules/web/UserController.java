package cn.etop.rbac.modules.web;

import cn.etop.rbac.common.util.MsgUtil;
import cn.etop.rbac.common.util.PermissionUtil;
import cn.etop.rbac.modules.model.User;
import cn.etop.rbac.modules.service.IUserService;
import cn.etop.rbac.modules.json.Msg;
import cn.etop.rbac.common.annotation.RequiredPermission;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.annotation.Inherited;
import java.util.HashMap;
import java.util.List;

/**
 * @version V1.0
 * @Description: 拦截器类，实现权限加载拦截、登录拦截
 * @author: TingFeng Zhang
 * @date: 2017/7/14 14:22
 */
@Controller
@RequestMapping("/")
public class UserController{

    @Autowired
    IUserService userService;

    @ResponseBody
    @RequestMapping("user/checkUserExit")
    /**
    * @Description:检查用户昵称是否存在
    * @param: [userName]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:Exception
    */
    public Msg checkUserExit(@RequestParam(value = "userName")String userName) throws Exception{
        boolean b = userService.checkUserNameExit(userName);
        if(b){
            return Msg.success();
        }
        else{
            return Msg.fail();
        }
    }

    @ResponseBody
    @RequiredPermission("通过关键字返回用户")
    @RequestMapping("user/getUserByKeyWord")
    /**
    * @Description:通过关键字查询用户并附带页码，以JSON方式返回
    * @param: [KeyWord, pn]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:Exception
    */
    public Msg getUserByKeyWord(@RequestParam(value = "KeyWord")String KeyWord,
                                @RequestParam(value = "pn",defaultValue ="1")Integer pn
                               ) throws Exception{
        PageHelper.startPage(pn, 10);
        List<User> users = userService.selectByKeyWord(KeyWord);
        PageInfo pageInfo=new PageInfo(users);
        Throwable t = new Throwable();
        boolean isHasPermission= PermissionUtil.hasPermission(this.getClass(),t.getStackTrace()[0].getMethodName());
        if(isHasPermission){
            return Msg.success().add("pageInfo",pageInfo);
        }else{
            return Msg.fail().add("returnMsg","你没有获得权限[通过关键字返回用户]");
        }
    }

    @ResponseBody
    @RequiredPermission("编辑时回显用户信息")
    @RequestMapping("user/getUserById")
    /**
    * @Description:编辑用户时显示原来用户的信息
    * @param: [ID]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:Exception
    */
    public Msg getUserById(@RequestParam(value = "ID")Long ID) throws Exception{
        User user = userService.selectByPrimaryKey(ID);
        return Msg.success().add("user",user);
    }

    @RequiredPermission("角色浏览")
    @RequiresPermissions("role:roleBrowse")
    @RequestMapping("user/roleBrowse")
    /**
    * @Description:浏览用户所拥有的所有角色
    * @param: [pn, wid, model]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView browse(@RequestParam(value = "pn",defaultValue ="1")Integer pn,
                               @RequestParam(value = "wid",defaultValue ="0")Long wid,
                               Model model) throws Exception{
        ModelAndView mav = new ModelAndView("useless/roleBrowse");
        PageHelper.startPage(pn, 10);

        List<User> users = userService.listRoles(wid);
        PageInfo pageInfo=new PageInfo(users);

        model.addAttribute("pageInfo",pageInfo);
        return mav;
    }


    @RequiredPermission("用户添加")
    @ResponseBody
    @RequestMapping("user/userAdd")
    /**
    * @Description:添加用户，以JSON方式返回
    * @param: [user, result]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:Exception
    */
    public Msg userAdd(@Valid User user, BindingResult result) throws Exception{
        Throwable t = new Throwable();
        boolean isHasPermission= PermissionUtil.hasPermission(this.getClass(),t.getStackTrace()[0].getMethodName());
        if(!isHasPermission)
            return Msg.noPermission().add("returnMsg","你没有获得权限[用户添加]");
        if (result.hasErrors()) {
            String msg= MsgUtil.returnErrorMsg(result);
            return Msg.reject().add("msg",msg);
        }

        boolean b = userService.checkUserNameExit(user.getName());
        boolean b1 = userService.checkUserAccountExit(user.getAccount());
        HashMap<String, String> errorMap = new HashMap<>();
        if(b){
            errorMap.put("#add_name","昵称已经存在！");
        }
        if(b1){
            errorMap.put("#add_account","帐号已经存在！");
        }
        if(b||b1){
            return Msg.fail().add("errorMap",errorMap);
        }else{
            return Msg.success();

        }
    }

    @ResponseBody
    @RequiredPermission("用户编辑")
    @RequestMapping("user/userEdit")
    /**
    * @Description:用户编辑，以JSON方式返回
    * @param: [user, result]
    * @returnType:cn.etop.rbac.modules.json.Msg
    * @Exception:Exception
    */
    public Msg userEdit(@Valid User user, BindingResult result) throws Exception{
        Throwable t = new Throwable();
        boolean isHasPermission= PermissionUtil.hasPermission(this.getClass(),t.getStackTrace()[0].getMethodName());
        if(!isHasPermission)
            return Msg.noPermission().add("returnMsg","你没有获得权限[用户编辑]");
        if (result.hasErrors()) {
            String msg = MsgUtil.returnErrorMsg(result);
            return Msg.reject().add("msg",msg);
        }

        User user1 = userService.selectByPrimaryKey(user.getId());
        boolean b = false;
        boolean b1 = false;
        if(!user.getAccount().equals(user1.getAccount())){
            b1 = userService.checkUserAccountExit(user.getAccount());
        }
        if(!user.getName().equals(user1.getName())){
            b=userService.checkUserNameExit(user.getName());
        }
        HashMap<String, String> errorMap = new HashMap<>();
        if(b){
            errorMap.put("#editName","昵称已经存在！");
        }
        if(b1){
            errorMap.put("#editAccount","帐号已经存在！");
        }
        if(b||b1){
            return Msg.fail().add("errorMap",errorMap);
        }else{
            userService.updateByPrimaryKey(user);
            return Msg.success();
        }
    }



    @RequiredPermission("用户删除")
    @RequiresPermissions("role:userDelete")
    @RequestMapping("user/userDelete")
    /**
    * @Description:删除用户
    * @param: [ID, httpServletRequest]
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:Exception
    */
    public ModelAndView userDelete(@RequestParam(value = "ID",defaultValue ="0")
     Long ID,HttpServletRequest httpServletRequest,@RequestParam(value = "pn",defaultValue ="1")Integer pn,@RequestParam(value = "keyWord",defaultValue ="") String keyWord) throws Exception{
        HttpSession session =
                httpServletRequest.getSession();
        Long id = (Long) session.getAttribute("id");
        if(id==ID){
            ModelAndView mav = new ModelAndView("400");
            mav.addObject("msg","无法删除自己！");
            return mav;
        }
        userService.deleteByPrimaryKey(ID);
        ModelAndView modelAndView = new ModelAndView("redirect:/homePage/userManagement");
        modelAndView.addObject("keyWord",keyWord);
        modelAndView.addObject("pn",pn);
        return modelAndView;
    }

}
