package cn.etop.rbac.modules.service;

import cn.etop.rbac.modules.model.User;

import java.util.List;

/**
 * @version V1.0
 * @Description:关于User业务逻辑接口
 * @author: TingFeng Zhang
 * @date: 2017/7/15 15:11
 */
public interface IUserService{
    List<User> getUsers() throws Exception;
    List<User> listRoles(Long id) throws Exception;
    User selectByPrimaryKey(Long id) throws Exception;
    List<User> selectByKeyWord(String keyWord) throws Exception;
    int deleteByPrimaryKey(Long id) throws Exception;
    boolean checkUserNameExit(String username) throws Exception;
    boolean checkUserAccountExit(String account) throws Exception;
    int insert(User record) throws Exception;
    int updateByPrimaryKey(User record) throws Exception;

}
