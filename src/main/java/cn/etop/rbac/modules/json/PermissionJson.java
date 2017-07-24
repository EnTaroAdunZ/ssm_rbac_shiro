package cn.etop.rbac.modules.json;

import cn.etop.rbac.modules.model.Permission;


/**
 * @version V1.0
 * @Description:分配权限时Ajax请求所需数据，包含权限是否存在信息以及权限名
 * @author: TingFeng Zhang
 * @date: 2017/7/16 11:08
 */
public class PermissionJson {
    Permission permission;
    boolean isExit;


    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }
}
