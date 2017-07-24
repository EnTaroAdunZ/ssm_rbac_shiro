package cn.etop.rbac.modules.service;

import cn.etop.rbac.modules.model.RoleToPermission;

/**
 * @version V1.0
 * @Description:关于RoleToPermission业务逻辑接口
 * @author: TingFeng Zhang
 * @date: 2017/7/15 15:11
 */
public interface IRoleToPermissionService {
    int addItem(RoleToPermission roleToPermission) throws Exception;
    int deleteItem(RoleToPermission roleToPermission) throws Exception;
    int deleteById(Long id) throws Exception;
}
