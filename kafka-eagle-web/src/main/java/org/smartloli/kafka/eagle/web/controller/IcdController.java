package org.smartloli.kafka.eagle.web.controller;

import org.apache.commons.lang.StringUtils;
import org.smartloli.kafka.eagle.web.pojo.Icd;
import org.smartloli.kafka.eagle.web.service.IcdService;
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
public class IcdController {
    @Autowired
    private IcdService icdService;
    @RequestMapping(value="/patient_analysis/icdlist",method = RequestMethod.GET)
    public ModelAndView showIcd(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patient_analysis/icd_10");
        return mav;
    }
    @ResponseBody
    @RequestMapping(value="/patient_analysis/icdlist/ajax",method = RequestMethod.GET)
    public Map getAllIcd(){
        List<Icd> icdList = icdService.getAllIcd();
        List<String[]> returnIcdList = new ArrayList<>();
        for (Icd icd : icdList) {
            String [] stringIcds = new String [4];
            if(icd.getId()!=null){
                stringIcds[0] = icd.getId().toString();
            } else {
                stringIcds[0] = "";
            }
            if(!StringUtils.isEmpty(icd.getIcdCode())){
                stringIcds[1] = icd.getIcdCode();
            } else {
                stringIcds[1] = "";
            }
            if(!StringUtils.isEmpty(icd.getDiseaseName())){
                stringIcds[2] = icd.getDiseaseName();
            }else{
                stringIcds[2] = "";
            }
            if(!StringUtils.isEmpty(icd.getHelpCode())) {
                stringIcds[3] = icd.getHelpCode();
            }else{
                stringIcds[3] = icd.getHelpCode();
            }

            returnIcdList.add(stringIcds);
        }
        Map<Object,List> map = new HashMap();
        map.put("data",returnIcdList);
        System.out.println(map);
        return map;

    }
    @ResponseBody
    @RequestMapping(value ="/patient_analysis/icd_add",method = RequestMethod.POST)
    public String add_icd(@RequestBody Icd icd, HttpServletRequest request){
        boolean flag = icdService.addIcd(icd) > 0 ? true : false;
        if (flag) {
            return "true";
        } else {
            request.setAttribute("msg","<div class='alert alert-danger'>编码已存在！不能重复添加！</div>");
            return "false";
        }
    }
    @ResponseBody
    @RequestMapping(value = "/icd/deleteIcdByCode",method = RequestMethod.GET)
    public String deleteIcd(@RequestParam("code") String code){
        //JSONArray jsonArray = JSONArray.parseArray(ids);
        if(!StringUtils.isEmpty(code)){
            icdService.deleteIcdBycode(code);
        }
        return "success";//
    }
    ///patient_analysis/icd_modify
    @ResponseBody
    @RequestMapping(value = "/patient_analysis/icd_modify",method = RequestMethod.POST)
    public String modifyIcd(@RequestBody Icd icd) {
        boolean flag = icdService.modifyIcd(icd) > 0 ? true : false;
        if (flag) {
            return "true";
        } else {
            return "false";
        }
    }

}
