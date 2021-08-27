package com.yunionyun.mcp.mcclient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunionyun.mcp.mcclient.keystone.TokenCredential;
import com.yunionyun.mcp.mcclient.managers.impl.CloudAccountManager;
import com.yunionyun.mcp.mcclient.managers.impl.CloudregionManager;
import com.yunionyun.mcp.mcclient.utils.LoggerUtils;
import junit.framework.TestCase;
import org.slf4j.Logger;

/**
 * @author zxc
 * @date 2020/03/02
 */
public class CloudRegionManagerTest extends TestCase {
	private static Logger logger =
			LoggerUtils.createLoggerFor(CloudRegionManagerTest.class.getName());

	public void testAPP() {
		Client cli = new Client("", 5000, true, true);
		try {
			TokenCredential token = cli.Authenticate("", "", "", "");
			logger.info(token.toString());
			logger.debug(token.toString());
			Session s = cli.newSession("", null, EndpointType.AdminURL, token, "v2");
			JSONObject query = new JSONObject();
			CloudregionManager manager = new CloudregionManager(EndpointType.PublicURL);
			CloudAccountManager cloudAccountManager = new CloudAccountManager();
			query.put("scope", "system");
			JSONArray regionCities = manager.getRegionCities(s, query);
			if (regionCities != null) {
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
