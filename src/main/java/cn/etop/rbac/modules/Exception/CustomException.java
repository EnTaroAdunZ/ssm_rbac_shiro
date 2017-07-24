package cn.etop.rbac.modules.Exception;

/**
 * @version V1.0
 * @Description:定义一个简单的异常类
 * @author: TingFeng Zhang
 * @date: 2017/7/23 1:26
 */
public class CustomException extends Exception{
    //异常信息
    public String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
