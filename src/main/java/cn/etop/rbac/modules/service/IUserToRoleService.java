package cn.etop.rbac.modules.service;

import cn.etop.rbac.modules.model.UserToRole;

/**
 * @version V1.0
 * @Description:关于UserToRol业务逻辑接口
 * @author: TingFeng Zhang
 * @date: 2017/7/14 15:11
 */
public interface IUserToRoleService {
    int addItem(UserToRole userToRole) throws Exception;
    int deleteItem(UserToRole userToRole) throws Exception;
    int deleteById(Long id) throws Exception;
}
