/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartloli.kafka.eagle.web.controller;

import org.smartloli.kafka.eagle.web.pojo.FirstInfo;
import org.smartloli.kafka.eagle.web.pojo.Keonline;
import org.smartloli.kafka.eagle.web.pojo.LearningConfigure;
import org.smartloli.kafka.eagle.web.service.StreamMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Control user login, logout, reset password and other operations.
 * 
 * @author smartloli.
 *
 *         Created by May 26, 2017.
 */
@Controller
@RequestMapping("/onlineLearning")
public class StreamMasterController {

	@Autowired
	private StreamMasterService streamMasterService;

	@ResponseBody
	@RequestMapping(value="/toMatching")
	public void info(@RequestParam("userIds")List<String> users, @RequestParam("configureId")String configureId, @RequestParam("configureName")String configureName,HttpServletResponse response) throws Exception{
		streamMasterService.runStreamMaster(users, configureId,configureName);

		response.sendRedirect("/ke/onlineLearning/macthingViewPage");
	}

	@RequestMapping(value="/macthingViewPage")
	public ModelAndView MatchingViewPage(HttpServletRequest request)throws Exception{
		ModelAndView mv = new ModelAndView();
		System.out.println("1--------------------");
		mv.setViewName("/learning/macthingViewPage");
		return mv;
	}

	@RequestMapping(value="/managePattern")
	public ModelAndView managePattern(HttpServletRequest request)throws Exception{
		ModelAndView mv = new ModelAndView();
		List<Keonline> keonlines = streamMasterService.getAllKeonlines();
		mv.addObject("keonlines", keonlines);
		System.out.println(keonlines);
		mv.setViewName("/learning/managePattern");
		return mv;
	}
	//stopMatching
	@RequestMapping(value="/stopMatching")
	@ResponseBody
	public Map stopMatching(@RequestParam("id")String id,@RequestParam("configureId")String configureId, HttpServletRequest request)throws Exception{
		String str = streamMasterService.stopStreamMaster();
		String statusstr = "";
		if(str.equals("ok")){
			statusstr = "已完成";
		}else{
			statusstr = "正在匹配";
		}
		Map<String, String> map = new HashMap<>();
		String msg = "";
		Keonline keonline = new Keonline();
		keonline.setStatus(statusstr);
		boolean flag = streamMasterService.updateStatus(id,statusstr) > 0 ? true : false;;
		if (flag) {
			msg =  "success";
		} else {
			msg =  "failed";
		}
		map.put("msg", msg);
		return map;
	}
}
