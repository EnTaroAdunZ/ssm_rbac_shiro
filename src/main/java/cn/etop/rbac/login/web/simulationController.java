package cn.etop.rbac.login.web;

import cn.etop.rbac.common.annotation.RequiredPermission;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 63574 on 2017/7/16.
 */
@Controller
@RequestMapping("/")
public class simulationController {

    @RequiredPermission("播放音乐")
    @RequiresPermissions("simulationController:playMusic")
    @RequestMapping("simulationController/playMusic")
    /**
    * @Description:功能模拟
    * @param:
    * @returnType:org.springframework.web.servlet.ModelAndView
    * @Exception:
    */
    public ModelAndView permissionBrowse(){
     return new ModelAndView("notpermission") ;
    }
}


