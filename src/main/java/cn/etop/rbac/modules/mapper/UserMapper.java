package cn.etop.rbac.modules.mapper;

import cn.etop.rbac.modules.model.User;
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
public interface UserMapper {

    /**
     * 通过ID删除用户
     * @param id
     * @return
     * @throws Exception
     */
    int deleteByPrimaryKey(@Param("id") Long id) throws Exception;

    /**
     * 插入新用户
     * @param record
     * @return
     * @throws Exception
     */
    int insert(User record) throws Exception;

    /**
     * 更新用户
     * @param record
     * @return
     * @throws Exception
     */
    int updateByPrimaryKey(User record) throws Exception;

    /**
     * 根据ID查询用户
     * @param id
     * @return
     * @throws Exception
     */
    User selectByPrimaryKey(@Param("id")Long id) throws Exception;


    /**
     * 返回所有用户
     * @param
     * @return
     * @throws Exception
     */
    List<User> selectAll() throws Exception;

    /**
     * 查找为该账户的用户
     * @param account
     * @return User
     * @throws Exception
     */
    User selectByAccount(@Param("account")String account) throws Exception;

    /**
     * 查找为该昵称的用户
     * @param name
     * @return User
     * @throws Exception
     */
    User selectByName(@Param("name")String name) throws Exception;

    /**
     * 返回该用户所有角色
     * @param id
     * @return List<User>
     * @throws Exception
     */
    List<User> listPermission(@Param("wid")Long id) throws Exception;

    /**
     * 查询昵称、用户名包含该关键字的用户
     * @param keyWord
     * @return List<User>
     * @throws Exception
     */
    List<User> selectByKeyWord(@Param("keyWord")String keyWord) throws Exception;
}