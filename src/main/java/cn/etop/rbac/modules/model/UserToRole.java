package cn.etop.rbac.modules.model;

import org.springframework.stereotype.Component;

/**
 * @version V1.1
 * @Description:由MyBatisGenerator工具自动生成的bean类
 * @author: TingFeng Zhang
 * @date: 2017/7/13 11:08
 */
@Component
public class UserToRole {
    private Long id;
    private User user;
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}