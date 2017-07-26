package cn.etop.rbac.common.util;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

/**
 * @version V1.0
 * @Description:
 * @author: TingFeng Zhang
 * @date: 2017/7/26 0:59
 */
public class ErrorUtil {

    public static ModelAndView returnErrorPage(BindingResult bindingResult){
        ModelAndView errorModelAndView = new ModelAndView("400");
        String msg = MsgUtil.returnErrorMsg(bindingResult);
        errorModelAndView.addObject("msg",msg);
        return errorModelAndView;
    }

}
