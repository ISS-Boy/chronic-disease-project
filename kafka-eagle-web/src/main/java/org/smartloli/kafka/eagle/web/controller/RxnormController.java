package org.smartloli.kafka.eagle.web.controller;

import org.apache.commons.lang.StringUtils;
import org.smartloli.kafka.eagle.web.pojo.Rxnorm;
import org.smartloli.kafka.eagle.web.service.RxnormService;
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
public class RxnormController {
    @Autowired
    private RxnormService rxnormService;
    @RequestMapping(value="/patient_analysis/Rxnormlist",method = RequestMethod.GET)
    public ModelAndView showIcd(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patient_analysis/rxnorm");
        return mav;
    }
    @ResponseBody
    @RequestMapping(value="/patient_analysis/getRxnormlist/ajax",method = RequestMethod.GET)
    public Map getRxnormList(){
        List<Rxnorm> rxnormList = rxnormService.getRxnormList();
        List<String[]> returnRxList = new ArrayList<>();
        for (Rxnorm rxnorm: rxnormList) {
            String [] stringRxnorms = new String[4];
            if(rxnorm.getId()!=null){
                stringRxnorms[0] = rxnorm.getId().toString();
            } else {
                stringRxnorms[0] = "";
            }
            if(!StringUtils.isEmpty(rxnorm.getRxcode())){
                stringRxnorms[1] = rxnorm.getRxcode();
            } else {
                stringRxnorms[1] = "";
            }
            if(!StringUtils.isEmpty(rxnorm.getRxDescription())){
                stringRxnorms[2] = rxnorm.getRxDescription();
            }else{
                stringRxnorms[2] = "";
            }
            if(!StringUtils.isEmpty(rxnorm.getHelpCode())) {
                stringRxnorms[3] = rxnorm.getHelpCode();
            }else{
                stringRxnorms[3] = "";
            }

            returnRxList.add(stringRxnorms);
        }
        Map<Object,List> map = new HashMap();
        map.put("data",returnRxList);
        System.out.println(map);
        return map;
    }
    //添加 /ke/patient_analysis/rxnorm_add
    @ResponseBody
    @RequestMapping(value ="/patient_analysis/rxnorm_add",method = RequestMethod.POST)
    public String add_icd(@RequestBody Rxnorm rxnorm, HttpServletRequest request){
        boolean flag = rxnormService.addRxnorm(rxnorm) > 0 ? true : false;
        if (flag) {
            return "true";
        } else {
            return "false";
        }
    }
    //删除
    @ResponseBody
    @RequestMapping(value = "/rxnorm/deleterxnormByCode",method = RequestMethod.GET)
    public String deleteIcd(@RequestParam("code") String code){
        //JSONArray jsonArray = JSONArray.parseArray(ids);
        if(!StringUtils.isEmpty(code)){
            rxnormService.deleteRxnormBycode(code);
        }
        return "success";//
    }
    //修改/ke/patient_analysis/rxnorm_modify
    @ResponseBody
    @RequestMapping(value = "/patient_analysis/rxnorm_modify",method = RequestMethod.POST)
    public String modifyIcd(@RequestBody Rxnorm rxnorm) {
        boolean flag = rxnormService.modifyRxnorm(rxnorm) > 0 ? true : false;
        if (flag) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 根据code 查找 dexscription
     * */
    @ResponseBody
    @RequestMapping(value = "patient_analysis/findRxnormDescription",method = RequestMethod.GET)
    public Map<String,String> findRxnormDescription(@RequestParam("rxcode") String rxcode) {
        String rxDescription = rxnormService.findRxnormDescription(rxcode).getRxDescription();
        Map<String,String> map = new HashMap<>();
        map.put("data",rxDescription);
        return map;
    }

}
