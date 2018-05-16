package org.smartloli.kafka.eagle.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.smartloli.kafka.eagle.web.pojo.Snomed;
import org.smartloli.kafka.eagle.web.service.SnomedService;
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
public class SnomedController {
    @Autowired
    private SnomedService snomedService;
    @RequestMapping(value="/patient_analysis/snomedlist",method = RequestMethod.GET)
    public ModelAndView showIcd(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patient_analysis/snomed");
        return mav;
    }
    @ResponseBody
    @RequestMapping(value="/patient_analysis/getSnomedlist/ajax",method = RequestMethod.GET)
    public Map getSnomedList(){
        List<Snomed> snomedList = snomedService.getSnomedList();
        List<String[]> returnIcdList = new ArrayList<>();
        for (Snomed snomed : snomedList) {
            String [] stringIcds = new String [4];
            if(snomed.getId()!=null){
                stringIcds[0] = snomed.getId().toString();
            } else {
                stringIcds[0] = "";
            }
            if(!StringUtils.isEmpty(snomed.getSnomedCode())){
                stringIcds[1] = snomed.getSnomedCode();
            } else {
                stringIcds[1] = "";
            }
            if(!StringUtils.isEmpty(snomed.getSnomedCnomen())){
                stringIcds[2] = snomed.getSnomedCnomen();
            }else{
                stringIcds[2] = "";
            }
            if(!StringUtils.isEmpty(snomed.getHelpCode())) {
                stringIcds[3] = snomed.getHelpCode();
            }else{
                stringIcds[3] = "";
            }

            returnIcdList.add(stringIcds);
        }
        Map<Object,List> map = new HashMap();
        map.put("data",returnIcdList);
        System.out.println(map);
        return map;
    }

    @RequestMapping(value ="/patient_analysis/snomed_add",method = RequestMethod.POST)
    public String add_snomed(HttpServletRequest request, Snomed snomed){
        String snomed_code = request.getParameter("ke_snomed_code");
        String snomedCnomen = request.getParameter("ke_snomedCnomen");
        String helpCode = request.getParameter("ke_helpCode");
        snomed.setSnomedCode(snomed_code);
        snomed.setSnomedCnomen(snomedCnomen);
        snomed.setHelpCode(helpCode);
        if(snomedService.addSnomed(snomed)){
            return "redirect:/patient_analysis/snomedlist";
        }else{
            return "redirect:/errors/500";
        }

//        }

    }

    @RequestMapping(value = "/patient_analysis/checkSnomedCode",method = RequestMethod.GET)
    public @ResponseBody String checkSnomedCode(@RequestParam("snomedCode") String snomedCode){
        return snomedService.checkSnomedCode(snomedCode) > 0 ? "true" : "false";
    }

    @ResponseBody
    @RequestMapping(value = "/snomed/deleteSnomedByCode",method = RequestMethod.GET)
    public String deleteSnomed(@RequestParam("code") String code){
        //JSONArray jsonArray = JSONArray.parseArray(ids);
        if(!StringUtils.isEmpty(code)){
            snomedService.deleteSnomedBycode(code);
        }
        return "success";//
    }

    @ResponseBody
    @RequestMapping(value = "/patient_analysis/snomed_modify",method = RequestMethod.POST)
    public String modifySnomed(@RequestBody Snomed snomed) {
        boolean flag = snomedService.modifySnomed(snomed) > 0 ? true : false;
        if (flag) {
            return "true";
        } else {
            return "false";
        }
    }
    /**
     * 根据编码查找snomed
     * */
    @ResponseBody
    @RequestMapping(value = "patient_analysis/findSnomedCnomen",method = RequestMethod.GET)
    public Map<String,String> findSnomedCnomen(@RequestParam("scode") String scode) {
        String sCnomen = snomedService.findSnomedCnomen(scode).getSnomedCnomen();
        Map<String,String> map = new HashMap<>();
        map.put("data",sCnomen);
        return map;
    }
}
