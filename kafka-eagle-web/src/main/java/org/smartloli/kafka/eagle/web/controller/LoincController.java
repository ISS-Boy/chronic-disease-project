package org.smartloli.kafka.eagle.web.controller;

import org.apache.commons.lang.StringUtils;
import org.smartloli.kafka.eagle.web.pojo.Loinc;
import org.smartloli.kafka.eagle.web.service.LoincService;
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
public class LoincController {
    @Autowired
    private LoincService loincService;

    @RequestMapping(value = "/patient_analysis/loinclist", method = RequestMethod.GET)
    public ModelAndView showLoinc() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patient_analysis/loinc");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/patient_analysis/getLoinclist/ajax", method = RequestMethod.GET)
    public Map getLoincList() {
        List<Loinc> loincList = loincService.getLoincList();
        List<String[]> returnLoincList = new ArrayList<>();
        for (Loinc loinc : loincList) {
            String[] strings = new String[4];
            if (loinc.getId()!=null) {
                strings[0] = loinc.getId().toString();
            } else {
                strings[0] = "";
            }
            if (!StringUtils.isEmpty(loinc.getLoincCode())) {
                strings[1] = loinc.getLoincCode();
            } else {
                strings[1] = "";
            }
            if (!StringUtils.isEmpty(loinc.getLoincComponent())) {
                strings[2] = loinc.getLoincComponent();
            } else {
                strings[2] = "";
            }
            if (!StringUtils.isEmpty(loinc.getLoincProperty())) {
                strings[3] = loinc.getLoincProperty();
            } else {
                strings[3] = "";
            }

            returnLoincList.add(strings);
        }
        Map<Object, List> map = new HashMap();
        map.put("data", returnLoincList);
        System.out.println(map);
        return map;
    }
    @ResponseBody
    @RequestMapping(value = "/patient_analysis/loinc_add", method = RequestMethod.POST)
    public String addloinc(@RequestBody Loinc loinc) {
//        if(loincService.checkLoincCode(loinc)) {
//            return "false";
//        }
       return loincService.addLoinc(loinc) > 0 ? "true" : "false";
    }
    //删除
    @ResponseBody
    @RequestMapping(value = "/loinc/deleteLoincByCode",method = RequestMethod.GET)
    public String deleteLoinc(@RequestParam("code") String code){
        //JSONArray jsonArray = JSONArray.parseArray(ids);
        if(!StringUtils.isEmpty(code)){
            loincService.deleteLoincBycode(code);
        }
        return "success";//
    }
    //修改 /patient_analysis/loinc_modify
    @ResponseBody
    @RequestMapping(value = "/patient_analysis/loinc_modify",method = RequestMethod.POST)
    public String modifyLoinc(@RequestBody Loinc loinc) {
        boolean flag = loincService.modifyLoinc(loinc) > 0 ? true : false;
        if (flag) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 根据code 查找 loincComponent
     * */
    @ResponseBody
    @RequestMapping(value = "patient_analysis/findloincComponent",method = RequestMethod.GET)
    public Map<String,String> findloincComponent(@RequestParam("lcode") String lcode) {
        String loincComponent = loincService.findloincComponent(lcode).getLoincComponent();
        Map<String,String> map = new HashMap<>();
        map.put("data",loincComponent);
        return map;
    }
}
