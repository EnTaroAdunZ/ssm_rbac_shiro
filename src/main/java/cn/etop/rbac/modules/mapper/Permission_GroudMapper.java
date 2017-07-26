package cn.etop.rbac.modules.mapper;

import cn.etop.rbac.modules.model.Permission_Groud;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Description:权限组mapper
 * @author: TingFeng Zhang
 * @date: 2017/7/25 16:28
 */
@Repository
public interface Permission_GroudMapper {

    /**
     * 获得权限组所有信息
     * @return 权限组所有
     */
    List<Permission_Groud> selectAll() throws Exception;
}
