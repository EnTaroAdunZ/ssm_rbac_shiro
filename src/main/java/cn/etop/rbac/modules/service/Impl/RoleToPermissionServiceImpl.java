package cn.etop.rbac.modules.service.Impl;

import cn.etop.rbac.modules.mapper.RoleToPermissionMapper;
import cn.etop.rbac.modules.model.RoleToPermission;
import cn.etop.rbac.modules.service.IRoleToPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version V1.0
 * @Description:关于Role业务逻辑实现类,方法注释见接口
 * @author: TingFeng Zhang
 * @date: 2017/7/14. 15:11
 */
/**
 * Created by 63574 on 2017/7/15.
 */
@Service
public class RoleToPermissionServiceImpl implements IRoleToPermissionService {
    @Autowired
    RoleToPermissionMapper roleToPermissionMapper;

    @Override
    /**
    * @Description:新增角色权限关系
    * @param: [userToRole]
    * @returnType:int
    * @Exception:Exception
    */
    public int addItem(RoleToPermission userToRole) throws Exception{
        return  roleToPermissionMapper.addItem(userToRole);
    }

    @Override
    /**
    * @Description:删除角色权限关系
    * @param: [userToRole]
    * @returnType:int
    * @Exception:Exception
    */
    public int deleteItem(RoleToPermission userToRole) throws Exception{
        return  roleToPermissionMapper.deleteItem(userToRole);
    }

    @Override
    /**
    * @Description:通过ID删除角色权限关系
    * @param: [id]
    * @returnType:int
    * @Exception:Exception
    */
    public int deleteById(Long id) throws Exception{
        return roleToPermissionMapper.deleteById(id);
    }
}
