package cn.etop.rbac.modules.model;

import org.springframework.stereotype.Component;

/**
 * @version V1.1
 * @Description:由MyBatisGenerator工具自动生成的bean类
 * @author: TingFeng Zhang
 * @date: 2017/7/13 11:08
 */
@Component
public class RoleToPermission {

    private Long id;
    private Role role;
    private Permission permission;

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}