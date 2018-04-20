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
package org.smartloli.kafka.eagle.web.service;

import java.util.Map;

import org.smartloli.kafka.eagle.common.protocol.AlarmInfo;

/**
 * Alarm org.smartloli.kafka.eagle.grafana.service interface.
 * 
 * @author smartloli.
 *
 *         Created by Jan 17, 2017.
 * 
 *         Update by hexiang 20170216
 */
public interface AlarmService {

	/** Add alarmer interface. */
	public Map<String, Object> add(String clusterAlias, AlarmInfo alarm);

	/** Delete alarmer interface. */
	public void delete(String clusterAlias, String group, String topic);

	/** Get alarmer interface. */
	public String get(String clusterAlias, String formatter);

	/** List alarmer information. */
	public String list(String clusterAlias);

}
