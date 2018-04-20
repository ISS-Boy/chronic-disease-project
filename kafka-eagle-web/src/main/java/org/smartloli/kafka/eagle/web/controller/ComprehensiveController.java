package org.smartloli.kafka.eagle.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
/**
 * @author LH
 *
 *
 * */
@Controller
public class ComprehensiveController {
    /** comprehensive mood Viewer */
    @RequestMapping(value = "/comprehensive/mood", method = RequestMethod.GET)
    public ModelAndView moodView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/comprehensive/mood");
        return mav;
    }

    /** comprehensive mood Viewer */
    @RequestMapping(value = "/comprehensive/exercise", method = RequestMethod.GET)
    public ModelAndView exerciseView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/comprehensive/exercise");
        return mav;
    }
    /** comprehensive exersise Viewer */
    @RequestMapping(value = "/comprehensive/filtrate", method = RequestMethod.GET)
    public ModelAndView filtrateView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/comprehensive/filtrate");
        return mav;
    }
}
