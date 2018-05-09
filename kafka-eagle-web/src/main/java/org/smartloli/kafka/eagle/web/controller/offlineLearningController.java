package org.smartloli.kafka.eagle.web.controller;

import com.iss.bigdata.health.elasticsearch.entity.Disease;
import org.smartloli.kafka.eagle.web.pojo.DiseaseDB;
import org.smartloli.kafka.eagle.web.pojo.Menu;
import org.smartloli.kafka.eagle.web.pojo.PatientInfo;
import org.smartloli.kafka.eagle.web.service.MenuService;
import org.smartloli.kafka.eagle.web.service.OffLineLearningService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            List<PatientInfo> patientInfos = offLineLearningService.searchPatientByConditions("1968-08-08", "1972-09-18", "F",null);
            mv.addObject("patients",patientInfos);
        } else if (type.equals("0")) {
            mv.addObject("patients",new ArrayList<PatientInfo>());
        }
        mv.setViewName("/learning/offlineLearnAdd");
        return mv;
    }

}
