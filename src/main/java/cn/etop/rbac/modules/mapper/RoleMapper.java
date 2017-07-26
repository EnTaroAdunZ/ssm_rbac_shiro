package cn.etop.rbac.modules.mapper;

import cn.etop.rbac.modules.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.1
 * @Description:由MyBatisGenerator工具自动生成的mapper接口
 * @author: TingFeng Zhang
 * @date: 2017/7/13 11:08
 */
@Repository
public interface RoleMapper {

    /**
     * 删除ID所属角色
     * @param id
     * @return
     * @throws Exception
     */
    int deleteByPrimaryKey(@Param("id")Long id) throws Exception;

    /**
     * 插入新角色
     * @param record
     * @return
     * @throws Exception
     */
    int insert(Role record) throws Exception;

    /**
     * 更新角色
     * @param record
     * @return
     * @throws Exception
     */
    int updateByPrimaryKey(Role record) throws Exception;

    /**
     * 返回ID所属权限
     * @param id
     * @return
     * @throws Exception
     */
    Role selectByPrimaryKey(@Param("id")Long id) throws Exception;

    /**
     * 返回所有角色
     * @param
     * @return List<Role>
     * @throws Exception
     */
    List<Role> selectAll() throws Exception;

    /**
     * 返回角色代码所属角色
     * @param sn
     * @return Role
     * @throws Exception
     */
    Role selectBySn(@Param("sn")String sn) throws Exception;

    /**
     * 返回角色名所属角色
     * @param name
     * @return Role
     * @throws Exception
     */
    Role selectByName(@Param("name")String name) throws Exception;

    /**
     * 查询角色所拥有的权限
     * @param wid
     * @return  List<Role>
     * @throws Exception
     */
    List<Role> listPermission(@Param("wid")Long wid) throws Exception;

    /**
     * 返回角色名包含关键字的角色
     * @param keyWord
     * @return  List<Role>
     * @throws Exception
     */
    List<Role> selectByKeyWord(@Param("keyWord")String keyWord) throws Exception;

    /**
     * 返回名字以及sn一致的角色
     * @param role
     * @return
     * @throws Exception
     */
    List<Role> checkIfCountAdd(Role role) throws Exception;


}