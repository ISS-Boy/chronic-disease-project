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
package org.smartloli.kafka.eagle.plugin.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC constants.
 * 
 * @author smartloli.
 *
 *         Created by Jul 7, 2017
 */
public interface JConstants {

	/** Get databases. */
	public static final String SHOW_DATABASES = "SHOW DATABASES";

	/** Get tables. */
	public static final String SHOW_TABLES = "SHOW TABLES";

	/** MySql type. */
	public static final String MYSQL = "mysql";

	/** MySql driver name. */
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

	/** Create database script. */
	public static String CREATE_DB_SQL = "CREATE DATABASE IF NOT EXISTS %s";

	public static final List<String> TBLS = Arrays.asList("ke_p_role", "ke_resources", "ke_role_resource", "ke_trend", "ke_user_role", "ke_users");

	static String CREATE_TABLE_KE_P_ROLE = "CREATE TABLE IF NOT EXISTS `ke_p_role` (`id` tinyint(4) NOT NULL AUTO_INCREMENT,`name` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT 'role name',`seq` tinyint(4) NOT NULL COMMENT 'rank',`description` varchar(128) CHARACTER SET utf8 NOT NULL COMMENT 'role describe',PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4";
	static String CREATE_TABLE_KE_P_ROLE_INSERT = "INSERT INTO `ke_p_role` VALUES ('1', 'Administrator', '1', 'Have all permissions'), ('2', 'Devs', '2', 'Own add or delete'), ('3', 'Tourist', '3', 'Only viewer')";

	static String CREATE_TABLE_KE_RESOURCES = "CREATE TABLE IF NOT EXISTS `ke_resources` (`resource_id` int(11) NOT NULL AUTO_INCREMENT,`name` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT 'resource name',`url` varchar(255) NOT NULL,`parent_id` int(11) NOT NULL,PRIMARY KEY (`resource_id`)) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4";
	static String CREATE_TABLE_KE_RESOURCES_INSERT = "INSERT INTO `ke_resources` VALUES ('1', 'System', '/system', '-1'), ('2', 'User', '/system/user', '1'), ('3', 'Role', '/system/role', '1'), ('4', 'Resource', '/system/resource', '1'), ('5', 'Notice', '/system/notice', '1'), ('6', 'Topic', '/topic', '-1'), ('7', 'Message', '/topic/message', '6'), ('8', 'Create', '/topic/create', '6'), ('9', 'Alarm', '/alarm', '-1'), ('10', 'Add', '/alarm/add', '9'), ('11', 'Modify', '/alarm/modify', '9'), ('12', 'Cluster', '/cluster', '-1'), ('13', 'ZkCli', '/cluster/zkcli', '12'), ('14', 'UserDelete', '/system/user/delete', '1'), ('15', 'UserModify', '/system/user/modify', '1'), ('16', 'Mock', '/topic/mock', '6')";

	static String CREATE_TABLE_KE_ROLE_RESOURCE = "CREATE TABLE IF NOT EXISTS `ke_role_resource` (`id` int(11) NOT NULL AUTO_INCREMENT,`role_id` int(11) NOT NULL,`resource_id` int(11) NOT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4";
	static String CREATE_TABLE_KE_ROLE_RESOURCE_INSERT = "INSERT INTO `ke_role_resource` VALUES ('1', '1', '1'), ('2', '1', '2'), ('3', '1', '3'), ('4', '1', '4'), ('5', '1', '5'), ('6', '1', '7'), ('7', '1', '8'), ('8', '1', '10'), ('9', '1', '11'), ('10', '1', '13'), ('11', '2', '7'), ('12', '2', '8'), ('13', '2', '13'), ('14', '2', '10'), ('15', '2', '11'), ('16', '1', '14'), ('17', '1', '15'), ('18', '1', '16')";

	static String CREATE_TABLE_KE_TREND = "CREATE TABLE IF NOT EXISTS `ke_trend` (`cluster` varchar(64) NOT NULL,`key` varchar(64) NOT NULL,`value` varchar(64) NOT NULL,`hour` varchar(2) NOT NULL,`tm` varchar(16) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

	static String CREATE_TABLE_KE_USER_ROLE = "CREATE TABLE IF NOT EXISTS `ke_user_role` (`id` int(11) NOT NULL AUTO_INCREMENT,`user_id` int(11) NOT NULL,`role_id` tinyint(4) NOT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4";
	static String CREATE_TABLE_KE_USER_ROLE_INSERT = "INSERT INTO `ke_user_role` VALUES ('1', '1', '1');";

	static String CREATE_TABLE_KE_USERS = "CREATE TABLE IF NOT EXISTS `ke_users` (`id` int(11) NOT NULL AUTO_INCREMENT,`rtxno` int(11) NOT NULL,`username` varchar(64) NOT NULL,`password` varchar(128) NOT NULL,`email` varchar(64) NOT NULL,`realname` varchar(128) NOT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4";
	static String CREATE_TABLE_KE_USERS_INSERT = "INSERT INTO `ke_users` VALUES ('1', '1000', 'admin', '123456', 'admin@email.com', 'Administrator');";

	/** Create tables script. */
	public static final Map<String, String> KEYS = new HashMap<String, String>() {

		private static final long serialVersionUID = 1L;

		{
			put("CREATE_TABLE_KE_P_ROLE", CREATE_TABLE_KE_P_ROLE);
			put("CREATE_TABLE_KE_P_ROLE_INSERT", CREATE_TABLE_KE_P_ROLE_INSERT);
			put("CREATE_TABLE_KE_RESOURCES", CREATE_TABLE_KE_RESOURCES);
			put("CREATE_TABLE_KE_RESOURCES_INSERT", CREATE_TABLE_KE_RESOURCES_INSERT);
			put("CREATE_TABLE_KE_ROLE_RESOURCE", CREATE_TABLE_KE_ROLE_RESOURCE);
			put("CREATE_TABLE_KE_ROLE_RESOURCE_INSERT", CREATE_TABLE_KE_ROLE_RESOURCE_INSERT);
			put("CREATE_TABLE_KE_TREND", CREATE_TABLE_KE_TREND);
			put("CREATE_TABLE_KE_USER_ROLE", CREATE_TABLE_KE_USER_ROLE);
			put("CREATE_TABLE_KE_USER_ROLE_INSERT", CREATE_TABLE_KE_USER_ROLE_INSERT);
			put("CREATE_TABLE_KE_USERS", CREATE_TABLE_KE_USERS);
			put("CREATE_TABLE_KE_USERS_INSERT", CREATE_TABLE_KE_USERS_INSERT);
		}

	};

	/** Mac system . */
	public static final String MAC = "Mac";

	/** Windows system. */
	public static final String WIN = "Win";

}
