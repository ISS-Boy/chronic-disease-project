package org.smartloli.kafka.eagle.web.controller;

import com.iss.bigdata.health.elasticsearch.entity.Disease;
import org.smartloli.kafka.eagle.web.pojo.*;
import org.smartloli.kafka.eagle.web.service.MenuService;
import org.smartloli.kafka.eagle.web.service.OffLineLearningService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by weidaping on 2018/5/7.
 */
@Controller
@RequestMapping(value="/offlineLearning")
public class offlineLearningController {

    @Resource(name="menuService")
    private MenuService menuService;

    @Resource(name="offLineLearning")
    private OffLineLearningService offLineLearningService;

    private Logger logger = Logger.getLogger(this.getClass().toString());

    /**
     * 请求新增离线学习任务页面
     * @return
     */
    @RequestMapping(value="/toAdd")
    public ModelAndView toAdd(@RequestParam("type") String type, HttpServletRequest request)throws Exception{
        ModelAndView mv = new ModelAndView();
        List<DiseaseDB> diseaseDB = offLineLearningService.getAllDiseaseDB();
        mv.addObject("allDisease", diseaseDB);
        if (type.equals("1")) {
            String gender = null;
            String startDate = null, endDate = null;
            List<String> conditions = null;
            if (request.getParameter("ageEnd") != null && request.getParameter("ageEnd") != "") {
                Long startAge = Long.valueOf(request.getParameter("ageEnd"));
                startDate = LocalDate.now().minusYears(startAge).toString();
            }
            if (request.getParameter("ageStart") != null && request.getParameter("ageStart") != "") {
                Long endAge = Long.valueOf(request.getParameter("ageStart"));
                endDate = LocalDate.now().minusYears(endAge).toString();
            }
            if (request.getParameter("disease") != null && request.getParameter("disease") != "") {
                String diseases = request.getParameter("disease");
                conditions = new ArrayList<>();
//                String[] condition = diseases.split(",disease,");
                conditions.add(diseases);
            }
            if (request.getParameter("gender") != null && request.getParameter("gender") != "") {
                gender = request.getParameter("gender");
            }
            System.out.println(conditions.size() + "===" + gender + "===" + startDate + "===" + endDate);
            List<PatientInfo> patientInfos = offLineLearningService.searchPatientByConditions(startDate, endDate, gender, conditions);
            System.out.println(patientInfos);
            mv.addObject("patients",patientInfos);
        } else if (type.equals("0")) {
            mv.addObject("patients",new ArrayList<PatientInfo>());
        }
        mv.setViewName("/learning/offlineLearnAdd");
        return mv;
    }


    /**
     * 请求新增离线学习任务页面
     * @return
     */
    @RequestMapping(value="/nextStep")
    public ModelAndView nextStep(@RequestParam("userIds")List<String> userIds, FirstInfo firstInfo, HttpServletRequest request)throws Exception{
        ModelAndView mv = new ModelAndView();
        System.out.println("========" + firstInfo.toString());
        mv.addObject("firstInfo", firstInfo);
        mv.addObject("userIds", userIds);
        System.out.println(userIds);
        mv.setViewName("/learning/offlineLearnList");
        return mv;
    }


    /**
     * 请求新增离线学习任务页面
     * @return
     */
    @RequestMapping(value="/startLearning")
    public ModelAndView startLearning(@RequestParam("userIds")List<String> userIds, FirstInfo firstInfo, HttpServletRequest request)throws Exception{
        ModelAndView mv = new ModelAndView();
        offLineLearningService.learning(null, userIds);
        System.out.println("========" + firstInfo.toString());
        mv.addObject("firstInfo", firstInfo);
        mv.addObject("userIds", userIds);
        System.out.println(userIds);
        mv.setViewName("/learning/offlineLearnList");
        return mv;
    }


    /**
     * 请求新增离线学习任务页面
     * @return
     */
    @RequestMapping(value="/runDemo")
    @ResponseBody
    public Map runDemo()throws Exception{
        Map<String, String> map = new HashMap<>();
        String msg = "success";
        LearningConfigure learningConfigure = new LearningConfigure();
        learningConfigure.setAge("50~55")
                .setAlphabetSize(4)
                .setAnalysisWindowStartSize(3)
                .setConfigureId(UUID.randomUUID().toString())
                .setConfigureName("runDemo")
                .setDateBegin("1483200060")
                .setDateEnd("1485910860")
                .setSlidingWindowSize(64)
                .setDisease("间歇性踌躇满志症")
                .setFrequencyThreshold(2)
                .setrThreshold(10000)
                .setPaaSize(8)
                .setGender("F")
                .setK(10)
                .setMetric("systolic_blood_pressure,metrics,diastolic_blood_pressure");


        List<String> userIds = new ArrayList<>();
        userIds.add("the-user-1");
        try {
            offLineLearningService.learning(learningConfigure, userIds);
        } catch (Exception e) {
            msg = "failed";
            logger.log(Level.WARNING, e.toString());
        }
        System.out.println("=========" + msg);
        map.put("msg", msg);
        return map;
    }

    /**
     * 请求新增离线学习任务页面
     * @return
     */
    @RequestMapping(value="/stopLearning")
    @ResponseBody
    public Map stopLearning(@RequestParam("configureId")String configureId, HttpServletRequest request)throws Exception{
        Map<String, String> map = new HashMap<>();
        String msg = "success";
        try {
            offLineLearningService.stopLearning(configureId);
        } catch (Exception e) {
            msg = "failed";
            logger.log(Level.WARNING, e.toString());
        }
        map.put("msg", msg);
        System.out.println(configureId);
        return map;
    }


    /**
     * 请求新增离线学习任务页面
     * @return
     */
    @RequestMapping(value="/getAllConfigure")
    public ModelAndView getAllConfigure(HttpServletRequest request)throws Exception{
        ModelAndView mv = new ModelAndView();
        List<LearningConfigure> learningConfigures = offLineLearningService.getAllConfigure();
        mv.addObject("learningConfigures", learningConfigures);
        System.out.println(learningConfigures);
        mv.setViewName("/learning/allConfigure");
        return mv;
    }


    /**
     * 请求新增离线学习任务页面
     * @return
     */
    @RequestMapping(value="/deleteConfigure")
    @ResponseBody
    public Map deleteConfigure(@RequestParam("configureId")String configureId, HttpServletRequest request)throws Exception{
        Map<String, String> map = new HashMap<>();
        String msg = "success";
        try {
            offLineLearningService.deleteConfigure(configureId);
        } catch (Exception e) {
            msg = "failed";
            logger.log(Level.WARNING, e.toString());
        }
        map.put("msg", msg);
        System.out.println(msg);
        return map;
    }


    /**
     * 请求新增离线学习任务页面
     * @return
     */
    @RequestMapping(value="/showResult")
    public ModelAndView showResult(@RequestParam("configureId")String configureId, HttpServletRequest request)throws Exception{
        ModelAndView mv = new ModelAndView();
        List<Pattern> patterns = offLineLearningService.showResult(configureId);
        mv.addObject("patterns", patterns);
        System.out.println(patterns.size());
        mv.setViewName("/learning/showResult");
        return mv;
    }

}
