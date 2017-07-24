package cn.etop.rbac.common.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @version V1.0
 * @Description:
 * @author: TingFeng Zhang
 * @date: 2017/7/24 12:48
 */
public class MsgUtil {

    public static String returnErrorMsg(BindingResult result){
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError1 : fieldErrors) {
            stringBuilder.append(fieldError1.getDefaultMessage()+"\n");
        }
        return stringBuilder.toString();
    }

}
