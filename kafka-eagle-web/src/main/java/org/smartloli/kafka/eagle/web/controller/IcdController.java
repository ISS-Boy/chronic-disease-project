package org.smartloli.kafka.eagle.web.controller;

import com.iss.bigdata.health.elasticsearch.entity.Medication;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.smartloli.kafka.eagle.web.pojo.Icd;
import org.smartloli.kafka.eagle.web.service.IcdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @RequestMapping(value ="/patient_analysis/icd_add",method = RequestMethod.POST)
    public String add_icd(HttpServletRequest request, Icd icd){
        String icd_code = request.getParameter("ke_icd_code");
        String diseaseName = request.getParameter("ke_diseaseName");
        String helpCode = request.getParameter("ke_helpCode");
        if(icd_code.equals(icd.getIcdCode())){
            request.setAttribute("msg","编码已存在！");
            return "redirect:/patient_analysis/icd_10";
        }else {
            icd.setIcdCode(icd_code);
            icd.setDiseaseName(diseaseName);
            icd.setHelpCode(helpCode);
            if (icdService.addIcd(icd)) {
                return "/patient_analysis/icd_10";
            } else {
                return "redirect:/errors/500";
            }
        }
    }
}
