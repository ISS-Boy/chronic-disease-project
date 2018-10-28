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

import org.smartloli.kafka.eagle.web.service.StreamMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

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
	public String info(@RequestParam("userIds")List<String> users,@RequestParam("configureId")String configureId) throws Exception{
		System.out.println(users + "," + configureId);
		streamMasterService.runStreamMaster(users, configureId);

		return "OK";
	}

}
