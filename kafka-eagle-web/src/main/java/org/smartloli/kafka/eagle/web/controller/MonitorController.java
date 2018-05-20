package org.smartloli.kafka.eagle.web.controller;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.smartloli.kafka.eagle.web.grafana.service.GrafanaDashboardService;
import org.smartloli.kafka.eagle.web.json.pojo.BlockGroup;
import org.smartloli.kafka.eagle.web.pojo.Monitor;
import org.smartloli.kafka.eagle.web.pojo.MonitorGroup;
import org.smartloli.kafka.eagle.web.service.MonitorGroupService;
import org.smartloli.kafka.eagle.web.service.MonitorService;
import org.smartloli.kafka.eagle.web.utils.DataValidatorUtil;
import org.smartloli.kafka.eagle.web.utils.ValidateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LH
 */
@Controller
public class MonitorController {
    String years_area = "2014";
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

    /**
     * whole-country Viewer
     */
    @RequestMapping(value = "/monitor/whole-country", method = RequestMethod.GET)
    public ModelAndView wholeView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/whole-country");
        return mav;
    }

    /**
     * area Viewer
     */
    @RequestMapping(value = "/monitor/area", method = RequestMethod.GET)
    public ModelAndView areaView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/area");
        return mav;
    }

    /**
     * city Viewer
     */
    @RequestMapping(value = "/monitor/city", method = RequestMethod.GET)
    public ModelAndView cityView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/city");
        return mav;
    }

    /**
     * location Viewer
     */
    @RequestMapping(value = "/monitor/location", method = RequestMethod.GET)
    public ModelAndView locationView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/location");
        return mav;
    }

    ///ke/monitor/monitor_block
    @RequestMapping(value = "/monitor/goto_monitor_block_add", method = RequestMethod.GET)
    public ModelAndView blockView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("monitor/monitor_block_add");
        return mav;
    }

    // 获取monitorGroup界面
    @RequestMapping(value = "/monitor/monitor_maintain", method = RequestMethod.GET)
    public ModelAndView monitorView(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("monitor/monitor_maintain");
        return mav;
    }

    @RequestMapping(value = "/monitor/submitMonitors", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String submitMonitors(@RequestBody String blocks, HttpSession session) throws IOException {
        logger.info(blocks);
        BlockGroup blockGroup = JSON.parseObject(blocks, BlockGroup.class);

        logger.info("========" + blockGroup + "=========");

        // 进行block检验
        List<ValidateResult> validateResults = DataValidatorUtil.validateBlocks(blockGroup);

        // 校验失败
        if (!(validateResults.size() == 1 &&
                validateResults.get(0).getResultCode() == ValidateResult.ResultCode.SUCCESS))
            return validateResults.toString();

        // 获取登陆者信息
//        Signiner user = (Signiner) session.getAttribute("LOGIN_USER_SESSION");
//        String creator = user.getRealname().toLowerCase();
        String creator = "the-user-1";

        // 校验成功, 开始创建镜像
        logger.info("======数据校验成功========");

        // 创建镜像, 若创建失败, 则会抛出异常到异常捕捉方法中
        monitorGroupService.createImage(creator, blockGroup);

        // 镜像创建成功
        return "success";
    }

    /**
     * 运行一个monitorGroup
     *
     * @param monitorGroupId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/monitor/runMonitorGroup", method = RequestMethod.GET)
    public String runMonitorGroup(@RequestParam("monitorGroupId") String monitorGroupId, HttpSession session) {
        logger.info("monitorGroupId:" + monitorGroupId);

        // 启动Monitor服务
        monitorGroupService.runService(monitorGroupId);

        session.setAttribute("monitorGroupId", monitorGroupId);
        return "redirect:/visualizer/visShow";
    }

    /**
     * 删除一个monitorGroup的镜像
     *
     * @param monitorGroupId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/monitor/deleteMonitorGroup", method = RequestMethod.GET)
    public String deleteMonitorGroup(@RequestParam("monitorGroupId") String monitorGroupId) throws Exception {
        logger.info("monitorGroupId:" + monitorGroupId);

        // 不能先停止服务后再删除镜像, 因为docker的异步清除机制,
        // Docker返回删除成功之后仍然尚未删除container
        // 此时立马删除镜像是有危险的
//         monitorGroupService.stopMonitorGroupService(monitorGroupId);

        // 再删除镜像
        monitorGroupService.deleteMonitorGroup(monitorGroupId);

        return "monitor/monitor_maintain";

    }

    /**
     * 停止MonitorGroup的Service
     *
     * @param monitorGroupId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/monitor/stopMonitorGroup", method = RequestMethod.GET)
    public String stopMonitorGroup(@RequestParam("monitorGroupId") String monitorGroupId) throws Exception {
        logger.info("monitorGroupId:" + monitorGroupId);

        // 停止MonitorGroupService
        monitorGroupService.stopMonitorGroupService(monitorGroupId);
        return "redirect:/visualizer/visShow";
    }

    @RequestMapping(value = "/monitor/showMonitorGroup", method = RequestMethod.GET)
    public ModelAndView showMonitorGroup(@RequestParam("monitorGroupId") String monitorGroupId) throws Exception {
        logger.info("monitorGroupId:" + monitorGroupId);
        ModelAndView mav = new ModelAndView("monitor/grafana-dashboard-test");

        List<String> urls = monitorGroupService.createMonitorDashBoardAndGetUrl(monitorGroupId);

        // 如果没有失败，则将数据url封装至ModelAndView中
        logger.info("==========" + urls + "==========");
        mav.addObject("urls", urls);
        return mav;
    }

    @RequestMapping(value = "/monitor/reviewMonitorGroup", method = RequestMethod.GET)
    public ModelAndView reviewMonitorGroup(@RequestParam("monitorGroupId") String monitorGroupId) {

        ModelAndView mav = new ModelAndView("monitor/monitor_review");
        List<Monitor> monitors = monitorService.getAllMonitorByGroupId(monitorGroupId);
        List<String> urls = monitors.stream().map(Monitor::getImgUrl).filter(s -> s != null).collect(Collectors.toList());
        mav.addObject("urls", urls);
        return mav;
    }

    /**
     * 获取monitorGroupList
     */
    @ResponseBody
    @RequestMapping(value = "/monitor/monitorGroupList", method = RequestMethod.GET)
    public Map getMonitorGroupList() {
        List<MonitorGroup> monitorGroupList = monitorGroupService.getAllMonitorGroups();
        Map<String, Object> map = new HashMap<>();
        map.put("data", JSON.toJSON(monitorGroupList));
        logger.info("==========" + map + "==========");
        return map;
    }

    /**whole-country Viewer* */
    @RequestMapping(value = "/monitor/whole_country", method = RequestMethod.GET)
    public ModelAndView whole_countryView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/whole_country");
        return mav;
    }
    @ResponseBody
    @RequestMapping(value = "/monitor/whole_country/setData",method = RequestMethod.GET)
    public JSONArray whole_country_setData(@RequestParam("value1") String value1,@RequestParam("value2") String value2,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        List<String > list=new ArrayList<String>();
        list = monitorService.getDireaseUserFM(value2,value1);
        JSONArray jsonArray = JSONArray.fromObject( list );//转化成json对象
        return jsonArray;
    }
    @ResponseBody
    @RequestMapping(value = "/monitor/whole_country/selected",method = RequestMethod.GET)
    public JSONArray whole_country_selected(@RequestParam("value1") String value1,@RequestParam("value2") String value2,HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        ArrayList arrayList = new ArrayList();
        arrayList.add("success");
        JSONArray jsonArray = JSONArray.fromObject(arrayList);
        return jsonArray;
    }

    /**disease_history_mon Viewer* */
    @RequestMapping(value = "/monitor/disease_history_mon",method = RequestMethod.GET)
    public ModelAndView monView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/disease_history_mon");
        return mav;
    }
    @ResponseBody
    @RequestMapping(value = "/monitor/disease_history_mon/setData",method = RequestMethod.GET)
    public JSONArray  mon_setData(@RequestParam("value1") String value1,@RequestParam("value2") String value2,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        ArrayList returnArraylist = monitorService.getDiseaseUserNum_mon(  value2,value1);
        JSONArray jsonArray = JSONArray.fromObject( returnArraylist );//转化成json对象
        return jsonArray;
    }
    @ResponseBody
    @RequestMapping(value = "/monitor/disease_history_mon/selected",method = RequestMethod.GET)
    public JSONArray mon_selected(@RequestParam("value1") String value1,@RequestParam("value2") String value2,HttpSession session){
        ArrayList arrayList = new ArrayList();
        arrayList.add("success");
        JSONArray jsonArray = JSONArray.fromObject(arrayList);
        return jsonArray;
    }

    /**disease_history_timeline Viewer* */
    @RequestMapping(value = "/monitor/disease_history_timeline",method = RequestMethod.GET)
    public ModelAndView timelineView(HttpSession session){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/monitor/disease_history_timeline");
        return mav;
    }
    @ResponseBody
    @RequestMapping(value = "/monitor/disease_history_timeline/setData",method = RequestMethod.GET)
    public JSONArray  timeline_setdata(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        Map returnArraylist = monitorService.getDiseaseUserNum_timeline();
        JSONArray jsonArray = JSONArray.fromObject( returnArraylist );//转化成json对象
        return jsonArray;
    }


    @ResponseBody
    @RequestMapping(value = "/monitor/longtitude/setData",method = RequestMethod.GET)
    public JSONArray  longtitude_setData(@RequestParam("value1") String value1,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        ArrayList returnArraylist = monitorService.getLongtitude(value1);
        JSONArray jsonArray = JSONArray.fromObject( returnArraylist );//转化成json对象
        return jsonArray;
    }
    @ResponseBody
    @RequestMapping(value = "/monitor/longtitude/selected",method = RequestMethod.GET)
    public JSONArray longtitude_selected(@RequestParam("value1") String value1){
        ArrayList arrayList = new ArrayList();
        arrayList.add("success");
        JSONArray jsonArray = JSONArray.fromObject(arrayList);
        return jsonArray;
    }

    @ResponseBody
    @RequestMapping(value = "/monitor/test", method = RequestMethod.POST)
    public String testMonitor2(@RequestBody String urls, HttpSession session) {
        logger.info(urls);
        String[] imgUrls = urls.split("ImageSeparator");
        session.setAttribute("urls", imgUrls);
        return "success";
    }

    @RequestMapping(value = "/monitor/getMonitorTest")
    public String getMonitorTest() {
        return "monitor/monitor_review";
    }

}
