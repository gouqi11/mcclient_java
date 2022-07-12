package com.yunionyun.mcp.mcclient;

import com.alibaba.fastjson.JSONObject;
import com.yunionyun.mcp.mcclient.keystone.TokenCredential;
import com.yunionyun.mcp.mcclient.managers.ListResult;
import com.yunionyun.mcp.mcclient.managers.impl.MonitorMetricManager;
import com.yunionyun.mcp.mcclient.utils.LoggerUtils;
import junit.framework.TestCase;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MetricTest extends TestCase {
	private static Logger logger = LoggerUtils.createLoggerFor(MetricTest.class.getName());

	public void testApp() {
		Client cli = new Client("", 5000, true, true);
		try {
			TokenCredential token =
					cli.Authenticate("", "", "", "", "");
			logger.info(token.toString());
			logger.debug(token.toString());
			Session s = cli.newSession("", null, null, token);
			MonitorMetricManager manager = new MonitorMetricManager();

			Set<String> sets = new HashSet<String>(Arrays.asList("test_metric01", "test_metric02"));

			// Test: Batch add metrics
			// manager.batchSubmit(s, sets);

			// Test: List all metrics
			ListResult listAll = manager.listAll(s);
			for (int i = 0; i < listAll.getDataSize(); i++) {
				JSONObject object = listAll.getDataAt(i);
				logger.debug(object.toJSONString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
