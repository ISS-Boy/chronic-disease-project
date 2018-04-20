package org.smartloli.kafka.eagle.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iss.bigdata.health.elasticsearch.entity.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.smartloli.kafka.eagle.web.pojo.Signiner;
import org.smartloli.kafka.eagle.web.service.PatientService;
import org.smartloli.kafka.eagle.web.sso.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author LH
 *
 *
 * */
@Controller
public class PatientController {
    String defaultUserId = "the-user-87";
   @Autowired
    private PatientService patientService;
/** Patient status_prognosis Viewer */
@RequestMapping(value = "/patient_analysis/status", method = RequestMethod.GET)
    public ModelAndView statusView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patient_analysis/status_prognosis");
        return mav;
    }

    /** Patient real-time-info Viewer */
    @RequestMapping(value = "/patient_analysis/realTime", method = RequestMethod.GET)
    public ModelAndView RealTimeView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient_analysis/realTimeInfo");
        return mav;
    }

    /** 个人数据分析 展示*/
    @RequestMapping(value = "/patient_analysis/perPatientInfo", method = RequestMethod.GET)
    public ModelAndView PerPatientView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient_analysis/perPatientAnalysisInfo");
        return mav;
    }

    /** 综合数据分析 展示*/
    @RequestMapping(value = "/patient_analysis/wholePatientInfo", method = RequestMethod.GET)
    public ModelAndView wholePatientInfoView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("patient_analysis/wholePatientAnalysisInfo");
        return mav;
    }

    /** Patient history_info Viewer */
    @RequestMapping(value = "/patient_analysis/history", method = RequestMethod.GET)
    public ModelAndView historyView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patient_analysis/history_info");
        return mav;
    }
    /** Patient history_detail Viewer */
    @RequestMapping(value = "/patient_analysis/history_detail",method = RequestMethod.GET)
    public ModelAndView historydetail(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patient_analysis/history_detail");
        return mav;
    }
//当前用户
    @RequestMapping(value="/patient_analysis/history/encounter",method = RequestMethod.GET)
    public ModelAndView getEncounterEvents(HttpServletRequest request,HttpSession session){
        Signiner user = ShiroUtils.getPrincipal();
        System.out.println(user.getId()+"-----");
        List<Encounter> encounterList = patientService.getEncounterEventsByUserId(defaultUserId);//6172
        request.setAttribute("encounterList",encounterList);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patient_analysis/history_info");
        return mav;
    }


    @RequestMapping(value="/patient_analysis/history/goToMultiHistoryPage",method = RequestMethod.GET)
    public ModelAndView goToMultiHistoryPage(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patient_analysis/compre_history_info");
        return mav;
    }

    //其它用户切换查看

    @ResponseBody
    @RequestMapping(value="/patient_analysis/history/getAllUserEncounter",method = RequestMethod.GET)
    public List<Encounter> getAllUserEncounter(@RequestParam(value="userId",required = false) String userId, HttpServletRequest request){
        List<Encounter>  encounterList = new ArrayList<>();
        if(Optional.ofNullable(userId).isPresent()) {
            encounterList = patientService.getEncounterEventsByUserId(userId);
        } else {
            Signiner user = ShiroUtils.getPrincipal();
//            encounterList = patientService.getEncounterEventsByUserId(String.valueOf(user.getRtxno()));
            encounterList = patientService.getEncounterEventsByUserId("the-user-87");
        }
        return encounterList;
    }

    @ResponseBody
    @RequestMapping(value = "/patient_analysis/history/getMedicationDetailById",method = RequestMethod.GET)
    public Map getMedicationDetail(@RequestParam("encounter_id")  String encounter_id,@RequestParam("userId") String userId, HttpServletResponse response, HttpServletRequest request){
       List<Medication> medicationList = patientService.getMedicationListByEncounterId(encounter_id,userId);
        List<String[]> returnList = new ArrayList<>();
        for (Medication medication : medicationList) {
            String [] strings = new String [6];
            if(!StringUtils.isEmpty(medication.getCode())) {
                strings[0] = medication.getCode();
            } else {
                strings[0] = "";
            }
            if(!StringUtils.isEmpty(medication.getDescription())) {
                strings[1] = medication.getDescription();
            } else {
                strings[1] = "";
            }
            if(medication.getDate() != null) {
                strings[2] = String.valueOf(medication.getDate());
            } else if(medication.getStart() != null){
                strings[2] = String.valueOf(medication.getStart());
            }else{
                strings[2] = "";
            }
            if(!StringUtils.isEmpty(medication.getDescription())) {
                strings[3] = medication.getDescription();
            } else {
                strings[3] = "";
            }
            if(!StringUtils.isEmpty(medication.getDescription())) {
                strings[4] = medication.getDescription();
            } else {
                strings[4] = "";
            }
            if(!StringUtils.isEmpty(medication.getDescription())) {
                strings[5] = medication.getDescription();
            } else {
                strings[5] = "";
            }
            returnList.add(strings);
        }
        Map<Object,List> map = new HashMap();
        map.put("data",returnList);
        System.out.println(map);
        return map;
    }
    @ResponseBody
    @RequestMapping(value = "/patient_analysis/history/getAllergyDetailById",method = RequestMethod.GET)
    public Map getAllergyDetail(@RequestParam("encounter_id") String encounter_id,@RequestParam("userId") String userId){
        List<String> allergyList =  patientService.getAllergyByEncounterId(encounter_id,userId);
        List dataArr = new ArrayList();
        allergyList.stream().forEach(str->{
            List allergyArray = new ArrayList();
            allergyArray.add(str);
            dataArr.add(allergyArray);
        });
        Map<String,List> map = new HashMap();
        map.put("data",dataArr);
        System.out.println(map);
        return map;
    }
    @ResponseBody
    @RequestMapping(value = "/patient_analysis/history/getCarePlanDetailById",method = RequestMethod.GET)
    public Map getCarePlan(@RequestParam("encounter_id") String encounter_id,@RequestParam("userId") String userId){
        List<CarePlan> carePlanList = patientService.getCarePlanByEncounterId(encounter_id,userId);
        List<String[]> returnList = new ArrayList<>();
        for (CarePlan careplan:carePlanList) {
            String [] strings = new String [4];
            if (!StringUtils.isEmpty(careplan.getDescription())){
                strings[1] = careplan.getDescription();
            }else{
                strings[1] = "";
            }
            if (!StringUtils.isEmpty(careplan.getCode())){
                strings[0] = careplan.getCode();
            }else{
                strings[0] = "";
            }
            if (careplan.getDate()!=null){
                strings[2] = String.valueOf(careplan.getDate());
            }else if (careplan.getStart()!= null){
                strings[2] = String.valueOf(careplan.getStart());
            }else{
                strings[2] = "";
            }
            if(!StringUtils.isEmpty(careplan.getReasondescription())){
                strings[3] = careplan.getReasondescription();
            }else {
                strings[3] = "";
            }
            returnList.add(strings);
        }
        Map<Object,List> map = new HashMap();
        map.put("data",returnList);
        System.out.println(map);
        return map;
    }
//getConditionDetailById
    @ResponseBody
    @RequestMapping(value = "/patient_analysis/history/getConditionDetailById",method = RequestMethod.GET)
    public Map getCondition(@RequestParam("encounter_id") String encounterId,@RequestParam("userId") String userId){
        List<Condition> conditionList = patientService.getConditionByEncounterId(encounterId,userId);
        List<String[]> returnList = new ArrayList<>();
        for (Condition condition: conditionList) {
            String[] strings = new String[4];
            if(!StringUtils.isEmpty(condition.getCode())){
                strings[0] = condition.getCode();
            }else{
                strings[0] = "";
            }
            if(!StringUtils.isEmpty(condition.getDescription())){
                strings[1] = condition.getDescription();
            }else{
                strings[1] = "";
            }
            if(condition.getDate()!=null){
                strings[2] = String.valueOf(condition.getDate());
            }else if(condition.getStart()!=null){
                strings[2] = String.valueOf(condition.getStart());
            }else{
                strings[2] = "";
            }
            if(!StringUtils.isEmpty(condition.getReasondescription())){
                strings[3] = condition.getReasondescription();
            }else {
                strings [3] = "";
            }
            returnList.add(strings);
        }
        Map<Object,List> map = new HashMap<>();
        map.put("data",returnList);
        return map;
    }
    @ResponseBody
    @RequestMapping(value = "/patient_analysis/history/getImmunizationDetailById",method = RequestMethod.GET)
    public Map getImmunization(@RequestParam("encounter_id") String encounterId,@RequestParam("userId") String userId) {
        List<Immunization> immunizationList = patientService.getImmunizationByEncounterId(encounterId,userId);
        List<String[]> returnList = new ArrayList<>();
        for (Immunization immunization: immunizationList) {
            String[] strings = new String[4];
            if(!StringUtils.isEmpty(immunization.getDescription())){
                strings[0] = immunization.getDescription();
            }else{
                strings[0] = "";
            }
            if(!StringUtils.isEmpty(immunization.getCode())){
                strings[1] = immunization.getCode();
            }else{
                strings[1] = "";
            }
            if(immunization.getDate()!=null){
                strings[2] = String.valueOf(immunization.getDate());
            }else if(immunization.getStart()!=null){
                strings[2] = String.valueOf(immunization.getStart());
            }else{
                strings[2] = "";
            }
            if(!StringUtils.isEmpty(immunization.getReasondescription())){
                strings[3] = immunization.getReasondescription();
            }else {
                strings [3] = "";
            }
            returnList.add(strings);
        }
        Map<Object,List> map = new HashMap<>();
        map.put("data",returnList);
        return map;
    }
    //getObservationDetailById
    @ResponseBody
    @RequestMapping(value = "/patient_analysis/history/getObservationDetailById",method = RequestMethod.GET)
    public Map getObservationSigns(@RequestParam("encounter_id") String encounterId,@RequestParam("userId") String userId){
        List<Signs> observationList = patientService.getObservationList(encounterId,userId);
        Map<String,Object> map = new HashMap<>();
        map.put("data",JSON.toJSON(observationList));
        return map;
    }

}

//{"data":["Allergy to dairy product","Allergy to dairy product","Allergy to dairy product"]}
//{"data":[["106258","Hydrocortisone 10 MG/ML Topical Cream","Mon Jun 07 00:00:00 CST 1971","Hydrocortisone 10 MG/ML Topical Cream","Hydrocortisone 10 MG/ML Topical Cream","Hydrocortisone 10 MG/ML Topical Cream"],["106258","Hydrocortisone 10 MG/ML Topical Cream","Mon Jun 07 00:00:00 CST 1971","Hydrocortisone 10 MG/ML Topical Cream","Hydrocortisone 10 MG/ML Topical Cream","Hydrocortisone 10 MG/ML Topical Cream"]]}