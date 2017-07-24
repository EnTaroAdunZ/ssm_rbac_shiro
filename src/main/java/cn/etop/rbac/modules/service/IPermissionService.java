package cn.etop.rbac.modules.service;

import cn.etop.rbac.modules.model.Permission;

import java.util.List;

/**
 * @version V1.0
 * @Description:关于Permission业务逻辑接口
 * @author: TingFeng Zhang
 * @date: 2017/7/14 15:11
 */
public interface IPermissionService {
    List<Permission> list() throws Exception;
//    void reload() throws Exception;
    void save(Permission permission) throws Exception;
    int insert(Permission record) throws Exception;
    int deleteByPrimaryKey(Long id) throws Exception;
    int updateByPrimaryKey(Permission record) throws Exception;
    Permission selectByPrimaryKey(Long id) throws Exception;
    List<Permission> selectByKeyWord(String keyWord) throws Exception;
}
