package org.smartloli.kafka.eagle.web.controller;

import org.apache.commons.lang.StringUtils;
import org.smartloli.kafka.eagle.web.pojo.Cvx;
import org.smartloli.kafka.eagle.web.service.CvxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author LH
 *
 *
 * */
@Controller
public class CvxController {
    @Autowired
    private CvxService cvxService;
    @RequestMapping(value="/patient_analysis/cvxlist",method = RequestMethod.GET)
    public ModelAndView showIcd(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patient_analysis/cvx");
        return mav;
    }
    @ResponseBody
    @RequestMapping(value="/patient_analysis/getCvxlist/ajax",method = RequestMethod.GET)
    public Map getCvx(){
        List<Cvx> cvxList = cvxService.getCvxList();
        List<String []> returnCvxList = new ArrayList<>();
        for (Cvx cvx : cvxList) {
            String [] stringcvxs = new String[4];
            if(cvx.getId()!=null){
                stringcvxs[0] = cvx.getId().toString();
            } else {
                stringcvxs[0] = "";
            }
            if(!StringUtils.isEmpty(cvx.getCvxcode())){
                stringcvxs[1] = cvx.getCvxcode();
            } else {
                stringcvxs[1] = "";
            }
            if(!StringUtils.isEmpty(cvx.getCvxDescription())){
                stringcvxs[2] = cvx.getCvxDescription();
            }else{
                stringcvxs[2] = "";
            }
            if(!StringUtils.isEmpty(cvx.getHelpCode())) {
                stringcvxs[3] = cvx.getHelpCode();
            }else{
                stringcvxs[3] = "";
            }

            returnCvxList.add(stringcvxs);
        }
        Map<Object,List> map = new HashMap();
        map.put("data",returnCvxList);
        System.out.println(map);
        return map;
    }
}
