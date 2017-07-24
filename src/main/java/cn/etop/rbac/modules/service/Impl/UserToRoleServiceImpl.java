package cn.etop.rbac.modules.service.Impl;

import cn.etop.rbac.modules.mapper.UserToRoleMapper;
import cn.etop.rbac.modules.model.UserToRole;
import cn.etop.rbac.modules.service.IUserToRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version V1.0
 * @Description:关于UserToRoleService业务逻辑实现类
 * @author: TingFeng Zhang
 * @date: 2017/7/14 15:11
 */
@Service
public class UserToRoleServiceImpl implements IUserToRoleService {

    @Autowired
    UserToRoleMapper userToRoleMapper;


    @Override
    /**   
    * @Description:新增用户角色关系
    * @param: [userToRole]
    * @returnType:int
    * @Exception:Exception
    */
    public int addItem(UserToRole userToRole) throws Exception{
        return  userToRoleMapper.addItem(userToRole);
    }

    @Override
    /**   
    * @Description:删除角色用户关系
    * @param: [userToRole]
    * @returnType:int
    * @Exception:Exception
    */
    public int deleteItem(UserToRole userToRole) throws Exception{
        return
        userToRoleMapper.deleteItem(userToRole);
    }

    @Override
    /**   
    * @Description:通过ID删除用户角色关系
    * @param: [id]
    * @returnType:int
    * @Exception:Exception
    */
    public int deleteById(Long id) throws Exception{
        return userToRoleMapper.deleteById(id);
    }

}
