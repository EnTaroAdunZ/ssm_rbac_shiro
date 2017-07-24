package cn.etop.rbac.modules.json;

import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Description:json返回数据封装类，包含数据、返回信息、状态
 * @author: TingFeng Zhang
 * @date: 2017/7/16 11:08
 */
public class Msg {
    private int code;
    private String msg;
    private Map<String,Object> extend=new HashMap<String,Object>();

    public static Msg success(){
        Msg result = new Msg();
        result.setCode(100);
        result.setMsg("处理成功");
        return result;
    }

    public static Msg fail(){
        Msg result = new Msg();
        result.setCode(200);
        result.setMsg("处理失败");
        return result;
    }

    public static Msg noPermission(){
        Msg result = new Msg();
        result.setCode(250);
        result.setMsg("没有权限");
        return result;
    }

    public static Msg reject(){
        Msg result = new Msg();
        result.setCode(300);
        result.setMsg("拒绝请求");
        return result;
    }

    public Msg add(String key,Object value){
         this.getExtend().put(key,value);
         return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
