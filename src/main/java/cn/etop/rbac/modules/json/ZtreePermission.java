package cn.etop.rbac.modules.json;

import cn.etop.rbac.modules.model.Permission;
import cn.etop.rbac.modules.model.Permission_Groud;

/**
 * @version V1.0
 * @Description:
 * @author: TingFeng Zhang
 * @date: 2017/7/25 15:31
 */
public class ZtreePermission {
    String id;
    String pId;
    String name;
    Boolean checked;
    Boolean open;

    public ZtreePermission(){

    }

    public ZtreePermission(Permission permission){
        this.id="p."+permission.getId();
        this.pId="g."+permission.getParentID();
        this.name=permission.getName();
        this.open=false;
        this.checked=false;
    }

    public ZtreePermission(Permission_Groud permission_groud){
        this.id="g."+permission_groud.getId();
        this.pId="g."+permission_groud.getParentID();
        this.name=permission_groud.getName();
        this.checked=null;
        this.open=true;
    }

    public Boolean isOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
