package cn.etop.rbac.modules.service.Impl;

import cn.etop.rbac.common.util.PasswordUtil;
import cn.etop.rbac.modules.model.User;
import cn.etop.rbac.modules.service.IUserService;
import cn.etop.rbac.modules.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @Description:关于UserService业务逻辑实现类
 * @author: TingFeng Zhang
 * @date: 2017/7/14 15:11
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    /**
    * @Description:返回所有用户
    * @param:
    * @returnType:java.util.List<cn.etop.rbac.modules.model.User>
    * @Exception:Exception
    */
    public List<User> getUsers() throws Exception {
        return userMapper.selectAll();
    }

    @Override
    /**
    * @Description:返回该用户所拥有的角色
    * @param: [id]
    * @returnType:java.util.List<cn.etop.rbac.modules.model.User>
    * @Exception:Exception
    */
    public List<User> listRoles(Long id) throws Exception{
        return userMapper.listPermission(id);
    }

    @Override
    /**
    * @Description:通过ID查询用户
    * @param: [id]
    * @returnType:cn.etop.rbac.modules.model.User
    * @Exception:Exception
    */
    public User selectByPrimaryKey(Long id) throws Exception{
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    /**
    * @Description:查询昵称、账户包含该关键字的用户
    * @param: [keyWord]
    * @returnType:java.util.List<cn.etop.rbac.modules.model.User>
    * @Exception:Exception
    */
    public List<User> selectByKeyWord(String keyWord) throws Exception{
        return userMapper.selectByKeyWord(keyWord);
    }

    @Override
    /**
    * @Description:通过ID删除用户
    * @param: [id]
    * @returnType:int
    * @Exception:Exception
    */
    public int deleteByPrimaryKey(Long id) throws Exception{
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    /**
    * @Description:检查用户中是否存在该昵称
    * @param: [username]
    * @returnType:boolean
    * @Exception:Exception
    */
    public boolean checkUserNameExit(String username) throws Exception{
        User user = userMapper.selectByName(username);
        if(user!=null){
            return true;
        }
        return false;
    }

    @Override
    /**
    * @Description:检查用户中是否存在该账户
    * @param: [account]
    * @returnType:boolean
    * @Exception:Exception
    */
    public boolean checkUserAccountExit(String account) throws Exception{
        User user=userMapper.selectByAccount(account);
        if(user!=null){
            return true;
        }
        return false;
    }

    @Override
    /**
    * @Description:新增用户
    * @param: [record]
    * @returnType:int
    * @Exception:Exception
    */
    public int insert(User record) throws Exception{
        User user = userMapper.selectByAccount(record.getAccount());
        if(user!=null){
            throw new RuntimeException("帐号已经存在！请换一个帐号!");
        }
        User user1 = userMapper.selectByName(record.getName());
        if(user1!=null){
            throw new RuntimeException("昵称已经存在！请换一个昵称!");
        }

        //开始加密过程
        String generate = PasswordUtil.generate(record.getPassword());
        record.setPassword(generate);
        return userMapper.insert(record);
    }

    @Override
    /**
    * @Description:更新用户
    * @param: [record]
    * @returnType:int
    * @Exception:Exception
    */
    public int updateByPrimaryKey(User record) throws Exception{
        return userMapper.updateByPrimaryKey(record);
    }
}
