package com.yunionyun.mcp.mcclient;

import com.alibaba.fastjson.JSONObject;
import com.yunionyun.mcp.mcclient.keystone.TokenCredential;
import com.yunionyun.mcp.mcclient.managers.impl.keystone.ServicesV3Manager;
import com.yunionyun.mcp.mcclient.utils.LoggerUtils;
import junit.framework.TestCase;
import org.slf4j.Logger;

/**
 * @author zxc
 * @date 2020/03/03
 */
public class ServicesV3ManagerTest extends TestCase {
	private static Logger logger = LoggerUtils.createLoggerFor(ServicesV3ManagerTest.class.getName());

	public void testApp() {

		Client cli = new Client("", 5000, true, true);
		try {
			TokenCredential token = cli.Authenticate("", "", "", "", "");

			Session s = cli.newSession("", null, null, token);

			ServicesV3Manager v3Manager = new ServicesV3Manager(EndpointType.PublicURL);
			JSONObject jsonObject = v3Manager.GetSpecific(s, "common", "config", null);
			if (jsonObject != null) {
				System.out.println(jsonObject.toJSONString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
