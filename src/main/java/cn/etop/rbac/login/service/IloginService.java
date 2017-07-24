package cn.etop.rbac.login.service;


import cn.etop.rbac.modules.model.User;

/**
 * Created by 63574 on 2017/7/13.
 */
public interface IloginService {
    User login(User user) throws Exception;
    User loginWithId(Long id) throws Exception;
    User getUserByAccount(String account)throws Exception;
}
