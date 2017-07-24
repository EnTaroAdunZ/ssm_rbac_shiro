package cn.etop.rbac.modules.web;

import cn.etop.rbac.modules.model.User;
import cn.etop.rbac.modules.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by 63574 on 2017/7/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/spring-web.xml"})
public class userControllerTest {
    @Autowired
    IUserService iUserService;

    @Test
    public void  testUserKeyWord() throws Exception {

        List<User> users = iUserService.selectByKeyWord("root");
        for(User user:users){
            System.out.println(user);
        }
    }

}
