package org.smartloli.kafka.eagle.web.controller;

import org.apache.commons.lang.StringUtils;
import org.smartloli.kafka.eagle.web.pojo.Cvx;
import org.smartloli.kafka.eagle.web.service.CvxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @ResponseBody
    @RequestMapping(value ="/patient_analysis/cvx_add",method = RequestMethod.POST)
    public String add_icd(@RequestBody Cvx cvx, HttpServletRequest request){
        boolean flag = cvxService.addCvx(cvx) > 0 ? true : false;
        if (flag) {
            return "true";
        } else {
            return "false";
        }
    }

    //删除
    @ResponseBody
    @RequestMapping(value = "/cvx/deleteCvxByCode",method = RequestMethod.GET)
    public String deleteLoinc(@RequestParam("code") String code){
        if(!StringUtils.isEmpty(code)){
            cvxService.deleteCvxBycode(code);
        }
        return "success";//
    }
    //修改 /patient_analysis/loinc_modify
    @ResponseBody
    @RequestMapping(value = "/patient_analysis/cvx_modify",method = RequestMethod.POST)
    public String modifyLoinc(@RequestBody Cvx cvx) {
        boolean flag = cvxService.modifyCvx(cvx) > 0 ? true : false;
        if (flag) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 根据code 查找 cvxComponent
     * */
    @ResponseBody
    @RequestMapping(value = "patient_analysis/findCvxComponent",method = RequestMethod.GET)
    public Map<String,String> findloincComponent(@RequestParam("lcode") String lcode) {
        String loincComponent = cvxService.findCvxDescription(lcode).getCvxDescription();
        Map<String,String> map = new HashMap<>();
        map.put("data",loincComponent);
        return map;
    }
}
