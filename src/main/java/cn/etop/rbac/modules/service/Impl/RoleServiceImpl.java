package cn.etop.rbac.modules.service.Impl;

import cn.etop.rbac.modules.mapper.RoleMapper;
import cn.etop.rbac.modules.model.Role;
import cn.etop.rbac.modules.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @Description:关于Role业务逻辑实现类
 * @author: TingFeng Zhang
 * @date: 2017/7/14. 15:11
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleMapper roleMapper;


    @Override
    /**
     * @Description:得到所有角色
     * @param:
     * @returnType:java.util.List<cn.etop.rbac.modules.model.Role>
     * @Exception:Exception
     */
    public List<Role> getRoles() throws Exception{
        return roleMapper.selectAll();
    }


    @Override
    /**
    * @Description:通过ID删除ID
    * @param: [id]
    * @returnType:int
    * @Exception:
    */
    public int deleteByPrimaryKey(Long id) throws Exception{
        return roleMapper.deleteByPrimaryKey(id);
    }


    @Override
    /**
     * @Description:得到所有角色
     * @param:
     * @returnType:java.util.List<cn.etop.rbac.modules.model.Role>
     * @Exception:Exception
     */
    public List<Role> selectByKeyWord(String keyWord) throws Exception{
        return roleMapper.selectByKeyWord(keyWord);
    }

    @Override
    /**
    * @Description:新增角色
    * @param: [record]
    * @returnType:int
    * @Exception:Exception
    */
    public int insert(Role record) throws Exception{
        Role role = roleMapper.selectByName(record.getName());
        if(role!=null){
            throw new RuntimeException("该角色名已经存在，请重新输入！");
        }
        Role role1 = roleMapper.selectBySn(record.getSn());
        if(role1!=null){
            throw new RuntimeException("该角色代码已经存在，请重新输入!");
        }


        return roleMapper.insert(record);
    }

    @Override
    /**
    * @Description:通过ID查询角色
    * @param: [id]
    * @returnType:cn.etop.rbac.modules.model.Role
    * @Exception:Exception
    */
    public Role selectByPrimaryKey(Long id) throws Exception{
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    /**
    * @Description:编辑角色
    * @param: [record]
    * @returnType:int
    * @Exception:Exception
    */
    public int updateByPrimaryKey(Role record) throws Exception{
        return roleMapper.updateByPrimaryKey(record);
    }


    @Override
    /**
    * @Description:返回该ID角色所包含的
    * @param: [wid]
    * @returnType:java.util.List<cn.etop.rbac.modules.model.Role>
    * @Exception:Exception
    */
    public List<Role> listPermission(Long wid) throws Exception{
        List<Role> roles = roleMapper.listPermission(wid);
        return roles;
    }
}
