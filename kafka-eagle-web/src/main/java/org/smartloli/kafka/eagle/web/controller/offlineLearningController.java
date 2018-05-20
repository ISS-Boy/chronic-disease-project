package org.smartloli.kafka.eagle.web.controller;

import org.smartloli.kafka.eagle.web.pojo.*;
import org.smartloli.kafka.eagle.web.service.MenuService;
import org.smartloli.kafka.eagle.web.service.OffLineLearningService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

            String ageEnd = request.getParameter("ageEnd");
            if (!StringUtils.isEmpty(ageEnd)) {
                Long startAge = Long.valueOf(ageEnd);
                startDate = LocalDate.now().minusYears(startAge).toString();
                mv.addObject("ageEnd", ageEnd);
            }
            String ageStart = request.getParameter("ageStart");
            if (!StringUtils.isEmpty(ageStart)) {
                Long endAge = Long.valueOf(ageStart);
                endDate = LocalDate.now().minusYears(endAge).toString();
                mv.addObject("ageStart", ageStart);
            }
            String disease = request.getParameter("disease");
            if (!StringUtils.isEmpty(disease)) {
                String diseases = disease;
                conditions = new ArrayList<>();
//                String[] condition = diseases.split(",disease,");
                conditions.add(diseases);
                mv.addObject("disease", disease);
            }
            String genderOrigin = request.getParameter("gender");
            if (!StringUtils.isEmpty(genderOrigin)) {
                gender = genderOrigin;
                mv.addObject("gender", genderOrigin);
            }


            logger.info(conditions.size() + "===" + gender + "===" + startDate + "===" + endDate);
            List<PatientInfo> patientInfos = offLineLearningService.searchPatientByConditions(ageStart, ageEnd, gender, conditions);
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
        mv.setViewName("learning/offlineLearnNext");
        return mv;
    }

    @RequestMapping(value="/newNextStep")
    public ModelAndView newNextStep()throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("learning/offlineLearnNext");
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
        mv.setViewName("learning/offlineLearnNext");
        return mv;
    }


    @RequestMapping(value="/run", method = RequestMethod.POST)
    public String run(String userIds,
                   String ages,
                   String gender,
                   String diseases,
                   String metric,
                   String startTime,
                   String endTime,
                   String taskName,
                   int windowLength,
                   int segment,
                   int alphabetCount,
                   int windowSize,
                   int frequencyThreshold,
                   int distanceThreshold,
                   int patternCount) throws ParseException {

        Map<String, String> map = new HashMap<>();
        String msg = "success";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        LearningConfigure learningConfigure = new LearningConfigure();
        learningConfigure.setAge(ages.replaceAll(",age,", "~"))
                .setAlphabetSize(alphabetCount)
                .setAnalysisWindowStartSize(windowSize)
                .setConfigureId(UUID.randomUUID().toString())
                .setConfigureName(taskName)
                .setDateBegin(String.valueOf(sdf.parse(startTime).getTime() / 1000))
                .setDateEnd(String.valueOf(sdf.parse(endTime).getTime() / 1000))
                .setSlidingWindowSize(windowLength)
                .setDisease(diseases)
                .setFrequencyThreshold(frequencyThreshold)
                .setrThreshold(distanceThreshold)
                .setPaaSize(segment)
                .setGender(gender)
                .setK(patternCount)
                .setMetric(metric.replaceAll(",", ",metrics,"));


        List<String> users = Stream.of(userIds.replaceAll("\\[|\\]| ", "")
                                    .split(",")).collect(Collectors.toList());
        try {
            offLineLearningService.learning(learningConfigure, users);
        } catch (Exception e) {
            throw e;
        }
        System.out.println("=========" + msg);
        return "redirect:/offlineLearning/getAllConfigure";
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
                .setDateEnd("1483405260")
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


    /**
     * 请求新增离线学习任务页面
     * @return
     */
    @RequestMapping(value="/showGraph1")
    public ModelAndView showGraph1(@RequestParam("id")String id, HttpServletRequest request)throws Exception{
        ModelAndView mv = new ModelAndView();
        PatternDetail patternDetail = offLineLearningService.getDetailById(id);
        mv.addObject("patternDetail", patternDetail);
        System.out.println(patternDetail);
        mv.setViewName("/learning/graph");
        return mv;
    }

}
