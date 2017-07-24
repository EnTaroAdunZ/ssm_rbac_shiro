package cn.etop.rbac.modules.service;

import cn.etop.rbac.modules.model.User;
import cn.etop.rbac.modules.model.UserToRole;
import cn.etop.rbac.modules.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 63574 on 2017/7/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/spring-web.xml"})
public class UserToRoleTest {

    @Autowired
    IUserToRoleService userToRoleService;

    @Test
    public void testAddItem(){
        UserToRole userToRole = new UserToRole();
        Role role = new Role();
        User user = new User();
        role.setId(1L);
        user.setId(2L);
        userToRole.setRole(role);
        userToRole.setUser(user);
        try {
            userToRoleService.addItem(userToRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testdeleteItem() throws Exception {
        UserToRole userToRole = new UserToRole();
        Role role = new Role();
        User user = new User();
        role.setId(1L);
        user.setId(2L);
        userToRole.setRole(role);
        userToRole.setUser(user);
        userToRoleService.deleteItem(userToRole);
    }

}
