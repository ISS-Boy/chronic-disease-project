package org.smartloli.kafka.eagle.web.exception;

import org.apache.log4j.Logger;
import org.smartloli.kafka.eagle.web.exception.entity.NormalException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dujijun on 2018/4/16.
 */
@ControllerAdvice
public class ExceptionsHandler {

    Logger logger = Logger.getLogger(ExceptionHandler.class);

    /**
     * 处理资源连接异常
     * @param e
     * @return
     */
    @ExceptionHandler(ResourceAccessException.class)
    public ModelAndView handleConnectException(ResourceAccessException e){
        // 异常打印
        logger.error(e.getMessage());
        e.printStackTrace();

        // 匹配其中的地址项
        Pattern p = Pattern.compile("(?<=http://).+?:\\d+(?=/)");
        Matcher matcher = p.matcher(e.getMessage());
        matcher.find();
        String url = matcher.group();

        ModelAndView mv = new ModelAndView("/error/500");
        mv.addObject("exceptionName", "外部资源访问异常");
        if(!StringUtils.isEmpty(url))
            mv.addObject("exceptionContent", "请检查与" + url + "的连接");
        return mv;
    }

    @ExceptionHandler(NormalException.class)
    public ModelAndView handleNormalException(NormalException e){
        // 异常打印
        logger.error(e.getMessage());
        e.printStackTrace();

        ModelAndView mv = new ModelAndView("/error/500");
        mv.addObject("exceptionName", "数据异常");
        mv.addObject("exceptionContent", e.getMessage());
        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleOtherException(Exception e){
        // 异常打印
        logger.error(e.getMessage());
        e.printStackTrace();

        ModelAndView mv = new ModelAndView("/error/500");
        mv.addObject("exceptionName", "未知错误");
        mv.addObject("exceptionContent", e.getMessage());
        return mv;
    }
}
