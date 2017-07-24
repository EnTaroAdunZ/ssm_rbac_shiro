package cn.etop.rbac.test;

import cn.etop.rbac.modules.model.User;
import cn.etop.rbac.modules.service.IUserService;
import cn.etop.rbac.modules.mapper.UserMapper;
import cn.etop.rbac.modules.model.Role;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Random;

/**
 * Created by 63574 on 2017/7/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/spring-web.xml"})
public class insertUser {
    @Autowired
    UserMapper userMapper;

    @Autowired
    SqlSession sqlSession;

    @Autowired
    IUserService userService;



    @Test
    public void testListP() throws Exception {
        List<User> users = userService.listRoles(1L);
        System.out.println(users.size());
        for(User user:users){
            List<Role> roleList = user.getRoleList();
            System.out.println(roleList);
        }


    }

    @Test
    public void test() throws Exception {
        for(int i=3;i<=1001;i++){
            User user=new User();
            user.setName(i+"号机");
            user.setAccount("ztf"+i);
            user.setPassword(i+"ztf");
            Random random=new Random();
            int i1 = random.nextInt(140);
            user.setAge(i1);
            long l = random.nextLong()%100000000+100000000;
            user.setExperience(l);
            user.setPhone("0752-"+String.valueOf(l));
            userService.insert(user);
        }
    }

}
