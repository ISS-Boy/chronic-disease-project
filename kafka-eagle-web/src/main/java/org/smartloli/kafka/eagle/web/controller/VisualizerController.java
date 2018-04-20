package org.smartloli.kafka.eagle.web.controller;

import org.smartloli.kafka.eagle.web.exception.entity.NormalException;
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
public class VisualizerController {
    @RequestMapping(value = "/visualizer/visShow", method = RequestMethod.GET)
    public ModelAndView visShow(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/visualizer/sourceVisualizer");
        String monitorGroupId;
        if((monitorGroupId = (String)session.getAttribute("monitorGroupId")) != null)
            mav.addObject("monitorGroupId", monitorGroupId);
        session.removeAttribute("monitorGroupId");
        return mav;
    }
}
