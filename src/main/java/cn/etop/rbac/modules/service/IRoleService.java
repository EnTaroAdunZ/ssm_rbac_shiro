package cn.etop.rbac.modules.service;

import cn.etop.rbac.modules.model.Role;

import java.util.List;

/**
 * Created by 63574 on 2017/7/14.
 */

/**
 * @version V1.0
 * @Description:关于Role业务逻辑接口
 * @author: TingFeng Zhang
 * @date: 2017/7/14 15:11
 */
public interface IRoleService {
    List<Role> getRoles() throws Exception;
    int deleteByPrimaryKey(Long id) throws Exception;
    List<Role> selectByKeyWord(String keyWord) throws Exception;
    int insert(Role record) throws Exception;
    Role selectByPrimaryKey(Long id) throws Exception;
    int updateByPrimaryKey(Role record) throws Exception;
    List<Role> listPermission(Long wid) throws Exception;
}
