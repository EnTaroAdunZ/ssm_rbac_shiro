package cn.etop.rbac.modules.model;

/**
 * @version V1.0
 * @Description:权限组实体类
 * @author: TingFeng Zhang
 * @date: 2017/7/25 16:34
 */
public class Permission_Groud {
    Long id;
    String name;
    Long parentID;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentID() {
        return parentID;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }


}
