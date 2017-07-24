package cn.etop.rbac.modules.json;

import cn.etop.rbac.modules.model.Role;

/**
 * @version V1.0
 * @Description:分配角色时Ajax请求所需数据，包含角色是否存在信息以及角色名
 * @author: TingFeng Zhang
 * @date: 2017/7/17 11:08
 */
public class RoleJson {
    boolean isExit;
    Role role;


    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
