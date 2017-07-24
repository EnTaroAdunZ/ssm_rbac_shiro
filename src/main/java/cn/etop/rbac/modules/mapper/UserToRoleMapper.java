package cn.etop.rbac.modules.mapper;

import cn.etop.rbac.modules.model.UserToRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @version V1.1
 * @Description:由MyBatisGenerator工具自动生成的mapper接口
 * @author: TingFeng Zhang
 * @date: 2017/7/13 11:08
 */
@Repository
public interface UserToRoleMapper {

    /**
     * 新增用户角色关系
     * @param userToRole
     * @return
     * @throws Exception
     */
    int addItem(UserToRole userToRole) throws Exception;

    /**
     * 删除用户角色关系
     * @param userToRole
     * @return
     * @throws Exception
     */
    int deleteItem(UserToRole userToRole) throws Exception;

    /**
     * 删除用户角色关系
     * @param id
     * @return
     * @throws Exception
     */
    int deleteById(@Param("id") Long id) throws Exception;
}