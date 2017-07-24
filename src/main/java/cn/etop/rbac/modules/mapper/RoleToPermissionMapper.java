package cn.etop.rbac.modules.mapper;

import cn.etop.rbac.modules.model.RoleToPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @version V1.1
 * @Description:由MyBatisGenerator工具自动生成的mapper接口
 * @author: TingFeng Zhang
 * @date: 2017/7/13 11:08
 */
@Repository
public interface RoleToPermissionMapper {

    /**
     * 增加一对新的角色权限关系
     * @param roleToPermission
     * @return
     * @throws Exception
     */
    int addItem(RoleToPermission roleToPermission);

    /**
     * 删除一对权限角色关系
     * @param roleToPermission
     * @return
     * @throws Exception
     */
    int deleteItem(RoleToPermission roleToPermission);

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    int deleteById(@Param("id") Long id);
}