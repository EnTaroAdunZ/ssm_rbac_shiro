package cn.etop.rbac.modules.mapper;

import cn.etop.rbac.modules.model.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Permission mapper.
 * @version V1.1
 * @Description:由MyBatisGenerator工具自动生成的mapper接口
 * @author: TingFeng Zhang
 * @date: 2017 /7/13 11:08
 */
@Repository
public interface PermissionMapper {

    /**
     * 删除ID所属权限
     * @param id
     * @return
     * @throws Exception
     */
    int deleteByPrimaryKey(@Param("id")Long id) throws Exception;

    /**
     * 插入新的权限
     * @param record
     * @return
     * @throws Exception
     */
    int insert(Permission record) throws Exception;

    /**
     * 搜索权限包含关键字的权限
     * @param keyWord
     * @return
     * @throws Exception
     */
    List<Permission> selectByKeyWord(@Param("keyWord")String keyWord) throws Exception;

    /**
     * 找出ID所属的权限
     * @param id
     * @return Permission
     * @throws Exception
     */
    Permission selectByPrimaryKey(@Param("id")Long id) throws Exception;

    /**
     * 通过权限名找到权限
     * @param name
     * @return Permission
     * @throws Exception
     */
    Permission selectByName(@Param("name")String name) throws Exception;

    /**
     * 通过权限表达式找到权限
     * @param expression
     * @return Permission
     * @throws Exception
     */
    Permission selectByExpression(@Param("expression")String expression) throws Exception;

    /**
     * 得到所有权限
     * @param
     * @return List<Permission>
     * @throws Exception
     */
    List<Permission> selectAll() throws Exception;

    /**
     * 更新权限
     * @param record
     * @return
     * @throws Exception
     */
    int updateByPrimaryKey(Permission record) throws Exception;

    /**
     * 检查权限名，全新标识符是否存在
     * @param permission
     * @return
     * @throws Exception
     */
    List<Permission> selectIfExist(Permission permission) throws Exception;

}