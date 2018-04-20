package org.smartloli.kafka.eagle.web.controller;

import org.smartloli.kafka.eagle.web.pojo.DataTest;
import org.smartloli.kafka.eagle.web.service.DataTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * @author LH
 *
 *
 * */
@Controller
@RequestMapping("/system")
public class DataTestController {
    @Autowired
    private DataTestService dataTestService;

    @RequestMapping(value = "/datatest", method = RequestMethod.GET)
    public ModelAndView DataView(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        List<DataTest> dataTestList = this.dataTestService.getAllData();
        mv.setViewName("/system/datatest");
        mv.addObject("dataT",dataTestList);
        return mv;
    }
    /*add data*/
    @RequestMapping(value = "/datatest/add/",method = RequestMethod.POST)
    public String addData( HttpServletRequest request,DataTest dt){
        String dNumb = request.getParameter("ke_rtxno_name");
        String name = request.getParameter("ke_real_name");
        String description = request.getParameter("ke_user_name");
        String value = request.getParameter("ke_user_email");

        dt.setdNumb(Integer.parseInt(dNumb));
        dt.setName(name);
        dt.setDescription(description);
        dt.setValue(value);


      if(dataTestService.addData(dt) ) {
          return "redirect:/system/datatest";
      }else{
          return "redirect:/errors/500";
      }
    }

    /** Find data through the data id. */
    @RequestMapping(value = "/datatest/findDataById/{id}/ajax", method = RequestMethod.GET)
    public void findDataByIdAjax(@PathVariable("id") int id, HttpServletResponse response, HttpServletRequest request) {
        try {
            byte[] output = dataTestService.findDataById(id).getBytes();
            BaseController.response(output, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Delete data. */
    @RequestMapping(value = "/datatest/delete",method = RequestMethod.POST)
    public String deleteData(@RequestParam("id") int id, HttpServletRequest request){
        DataTest dta = new DataTest();
        dta.setId(id);
        dataTestService.deleteData(id);
        return "redirect:/system/datatest";
    }

/*modify Datatest*/
    @RequestMapping(value = "/datatest/modify/",method = RequestMethod.POST)
    public String modifyDatatest(HttpServletRequest request,HttpSession session,DataTest dt){
        String dNumb = request.getParameter("ke_rtxno_name_modify");
        String name = request.getParameter("ke_real_name_modify");
        String description = request.getParameter("ke_user_name_modify");
        String value = request.getParameter("ke_user_email_modify");
        String id = request.getParameter("ke_user_id_modify");

        dt.setdNumb(Integer.parseInt(dNumb));
        dt.setName(name);
        dt.setDescription(description);
        dt.setValue(value);
        dt.setId(Integer.parseInt(id));
        if(dataTestService.modify(dt) ){
            return "redirect:/system/datatest";
        }else{
            return "redirect:/errors/500";
        }
    }
}
