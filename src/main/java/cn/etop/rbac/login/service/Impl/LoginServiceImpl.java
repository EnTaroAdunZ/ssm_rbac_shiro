package cn.etop.rbac.login.service.Impl;


import cn.etop.rbac.common.util.PasswordUtil;
import cn.etop.rbac.login.service.IloginService;
import cn.etop.rbac.modules.model.User;
import cn.etop.rbac.modules.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version V1.0
 * @Description: 权限标记类
 * @author: TingFeng Zhang
 * @date: 2017/7/13 12:12
 */
@Service
public class LoginServiceImpl implements IloginService {

    @Autowired
    UserMapper userMapper;

    @Override
    /**
    * @Description:登录信息进行md5认证
    * @param: [user]
    * @returnType:cn.etop.rbac.modules.model.User
    * @Exception:Exception
    */
    public User login(User user) throws Exception{
        User temp = null;
        temp=userMapper.selectByAccount(user.getAccount());
        return temp;
//        if(temp==null){
//            return null;
//        }else{
//            boolean verify = PasswordUtil.verify(user.getPassword(), temp.getPassword());
//            if(verify){
//                return temp;
//            }else{
//                return null;
//            }
//        }

    }

    @Override
    public User loginWithId(Long id) throws Exception{
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    /**
    * @Description:根据帐户名返回帐号
    * @param: [account]
    * @returnType:cn.etop.rbac.modules.model.User
    * @Exception:Exception
    */
    public User getUserByAccount(String account) throws Exception {
        User user = userMapper.selectByAccount(account);
        return user;
    }
}
