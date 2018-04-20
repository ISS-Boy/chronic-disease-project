package org.smartloli.kafka.eagle.web.controller;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.smartloli.kafka.eagle.web.exception.entity.NormalException;
import org.smartloli.kafka.eagle.web.grafana.service.GrafanaDashboardService;
import org.smartloli.kafka.eagle.web.json.pojo.Block;
import org.smartloli.kafka.eagle.web.pojo.MonitorGroup;
import org.smartloli.kafka.eagle.web.service.MonitorGroupService;
import org.smartloli.kafka.eagle.web.service.MonitorService;
import org.smartloli.kafka.eagle.web.utils.DataValidator;
import org.smartloli.kafka.eagle.web.utils.ValidateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
public class MonitorController {
    String  years_area = "2014";
    String diseases_area = "Hypertension";
    String years_mon = "2018";
    String diseases_mon = "Hypertension";
    String years_per = "2018";
    private static Logger logger = Logger.getLogger(MonitorController.class);

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private MonitorGroupService monitorGroupService;

    @Autowired
    private GrafanaDashboardService grafanaDashboardService;

    /**whole-country Viewer*/
    @RequestMapping(value = "/monitor/whole-country", method = RequestMethod.GET)
    public ModelAndView wholeView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/whole-country");
        return mav;
    }

    /**area Viewer*/
    @RequestMapping(value = "/monitor/area", method = RequestMethod.GET)
    public ModelAndView areaView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/area");
        return mav;
    }
    /**city Viewer*/
    @RequestMapping(value = "/monitor/city", method = RequestMethod.GET)
    public ModelAndView cityView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/city");
        return mav;
    }
    /**location Viewer*/
    @RequestMapping(value = "/monitor/location", method = RequestMethod.GET)
    public ModelAndView locationView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/location");
        return mav;
    }

    ///ke/monitor/monitor_block
    @RequestMapping(value = "/monitor/goto_monitor_block_add",method = RequestMethod.GET)
    public ModelAndView blockView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("monitor/monitor_block_add");
        return mav;
    }

    // 获取monitorGroup界面
    @RequestMapping(value = "/monitor/monitor_maintain",method = RequestMethod.GET)
    public ModelAndView monitorView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("monitor/monitor_maintain");
        return mav;
    }

    @RequestMapping(value = "/monitor/submitMonitors", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String submitMonitors(@RequestBody String blocks) throws IOException {
        logger.info(blocks);
        List<Block> blocksEntity = JSON.parseArray(blocks, Block.class);

        // 进行block检验
        List<ValidateResult> validateResults = DataValidator.validateBlocks(blocksEntity);

        // 这里暂时
        String creator = "dujijun";

        // 校验失败，后面会补充显示失败原因
        if(!(validateResults.size() == 1
                && validateResults.get(0).getResultCode()
                            == ValidateResult.ResultCode.SUCCESS))
            return validateResults.toString();

        // 校验成功
        logger.info("======数据校验成功========");
        ValidateResult result = monitorGroupService.createImage(creator, blocksEntity);
        if(result.getResultCode() == ValidateResult.ResultCode.FAILURE)
            return result.getMes();

        return "success";
    }

    /**
     * 运行一个monitorGroup
     * @param monitorGroupId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/monitor/runMonitorGroup", method = RequestMethod.GET)
    public String runMonitorGroup(@RequestParam("monitorGroupId") String monitorGroupId, HttpSession session){
        logger.info("monitorGroupId:" + monitorGroupId);
        ValidateResult serviceExecutionResult = monitorGroupService.runService(monitorGroupId);
        if(serviceExecutionResult.getResultCode() != ValidateResult.ResultCode.SUCCESS)
            throw new NormalException(serviceExecutionResult.getMes());

//        ValidateResult dashboardCreatedResult = grafanaDashboardService.checkAndCreateDashboard(monitorGroupId);
//        if(dashboardCreatedResult.getResultCode() == ValidateResult.ResultCode.FAILURE)
//            throw new Exception(dashboardCreatedResult.getMes());
//        logger.info("============" + dashboardCreatedResult.getMes() + "============");

        session.setAttribute("monitorGroupId", monitorGroupId);
        return "redirect:/visualizer/visShow";
    }

    /**
     * 删除一个monitorGroup的镜像
     * @param monitorGroupId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/monitor/deleteMonitorGroup", method = RequestMethod.GET)
    public String deleteMonitorGroup(@RequestParam("monitorGroupId") String monitorGroupId) throws Exception {
        logger.info("monitorGroupId:" + monitorGroupId);
        // 先停止服务
        ValidateResult stopResult = monitorGroupService.stopMonitorGroupService(monitorGroupId);
        if(stopResult.getResultCode() != ValidateResult.ResultCode.SUCCESS)
            throw new NormalException(stopResult.getMes());

        // 再删除镜像
        ValidateResult validateResult = monitorGroupService.deleteMonitorGroup(monitorGroupId);
        if(validateResult.getResultCode() != ValidateResult.ResultCode.SUCCESS)
            throw new NormalException(validateResult.getMes());

        return "monitor/monitor_maintain";

    }

    /**
     * 停止MonitorGroup的Service
     * @param monitorGroupId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/monitor/stopMonitorGroup", method = RequestMethod.GET)
    public String stopMonitorGroup(@RequestParam("monitorGroupId") String monitorGroupId) throws Exception {
        logger.info("monitorGroupId:" + monitorGroupId);
        ValidateResult validateResult = monitorGroupService.stopMonitorGroupService(monitorGroupId);
        if(validateResult.getResultCode() == ValidateResult.ResultCode.SUCCESS)
            return "redirect:/visualizer/visShow";
        else
            throw new NormalException(validateResult.getMes());
    }

    @RequestMapping(value = "/monitor/showMonitorGroup", method = RequestMethod.GET)
    public String showMonitorGroup(@RequestParam("monitorGroupId") String monitorGroupId) throws Exception {
        logger.info("monitorGroupId:" + monitorGroupId);
        ValidateResult validateResult = monitorGroupService.showMonitorDashBoard(monitorGroupId);
        if(validateResult.getResultCode() == ValidateResult.ResultCode.SUCCESS)
            return "monitor/monitor_dashboard";
        else
            throw new Exception(validateResult.getMes());
    }

    /**
     * 获取monitorGroupList
     * */
    @ResponseBody
    @RequestMapping(value = "/monitor/monitorGroupList", method = RequestMethod.GET)
    public Map getMonitorGroupList() {
        List<MonitorGroup> monitorGroupList = monitorGroupService.getAllMonitorGroups();
        Map<String, Object> map = new HashMap<>();
        map.put("data", JSON.toJSON(monitorGroupList));
        return map;
    }

    //通过ajax请求数据 将请求的数据返回到页面进行地图区域的显示
    @ResponseBody
    @RequestMapping(value = "/monitor/countryFM",method = RequestMethod.GET)
    public JSONArray getDiseaseUserFM(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        List<String > list=new ArrayList<String>();
        list = monitorService.getDireaseUserFM(diseases_area,years_area);
        JSONArray jsonArray = JSONArray.fromObject( list );//转化成json对象
        return jsonArray;
    }
    @ResponseBody
    @RequestMapping(value = "/monitor/whole_country/getData",method = RequestMethod.GET)
    public String getData(@RequestParam("value1") String value1,@RequestParam("value2") String value2){
        years_area = value1;
        diseases_area = value2;
        return "success";
    }

//将请求的数据返回到页面进行月份的显示

    @RequestMapping(value = "/monitor/disease_history_mon",method = RequestMethod.GET)
    public ModelAndView diseaseView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/disease_history_mon");
        return mav;
    }
    @ResponseBody
    @RequestMapping(value = "/monitor/disease_history_mon/getData",method = RequestMethod.GET)
    public String getData_mon(@RequestParam("value1") String value1,@RequestParam("value2") String value2,HttpSession session){
        years_mon = value1.toString();
        diseases_mon = value2.toString();
        System.out.println("-----"+years_mon+diseases_mon);
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/monitor/disease_history_mon/setData",method = RequestMethod.GET)
    public JSONArray  getDiseaseUserNum(HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");
        ArrayList returnArraylist = monitorService.getDiseaseUserNum_mon(  diseases_mon,years_mon);
        JSONArray jsonArray = JSONArray.fromObject( returnArraylist );//转化成json对象
        return jsonArray;
    }


    @RequestMapping(value = "/monitor/disease_history_per",method = RequestMethod.GET)
    public ModelAndView returnhisory_per(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/disease_history_per");
        return mav;
    }
    @ResponseBody
    @RequestMapping(value = "/monitor/disease_history_per/getData",method = RequestMethod.GET)
    public String getData_per(@RequestParam("value1") String value1,@RequestParam("value2") String value2){
        years_per = value1.toString();
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/monitor/disease_history_per/setData",method = RequestMethod.GET)
    public JSONArray  getDiseaseUserNum_per(HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");
        ArrayList returnArraylist = monitorService.getDiseaseUserNum_per(years_per );
        JSONArray jsonArray = JSONArray.fromObject( returnArraylist );//转化成json对象
        return jsonArray;
    }

    @RequestMapping(value = "/monitor/disease_history_timeline",method = RequestMethod.GET)
    public ModelAndView returnhisory_timeline(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/disease_history_timeline");
        return mav;
    }
    @ResponseBody
    @RequestMapping(value = "/monitor/disease_history_timeline/setData",method = RequestMethod.GET)
    public JSONArray  getDiseaseUserNum_disease_timeline(HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");
        Map returnArraylist = monitorService.getDiseaseUserNum_timeline();
        JSONArray jsonArray = JSONArray.fromObject( returnArraylist );//转化成json对象
        return jsonArray;
    }

}
