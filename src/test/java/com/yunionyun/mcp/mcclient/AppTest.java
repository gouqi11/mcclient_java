package com.yunionyun.mcp.mcclient;

import com.alibaba.fastjson.JSONObject;
import com.yunionyun.mcp.mcclient.keystone.TokenCredential;
import com.yunionyun.mcp.mcclient.managers.ListResult;
import com.yunionyun.mcp.mcclient.managers.impl.cloudid.SamlUserManager;
import com.yunionyun.mcp.mcclient.managers.impl.image.ImageManager;
import com.yunionyun.mcp.mcclient.managers.impl.compute.SkuManager;
import com.yunionyun.mcp.mcclient.managers.impl.compute.ServerManager;
import com.yunionyun.mcp.mcclient.managers.impl.compute.ServerDiskManager;
import com.yunionyun.mcp.mcclient.managers.impl.compute.HostManager;
import com.yunionyun.mcp.mcclient.managers.impl.keystone.ProjectManager;
import com.yunionyun.mcp.mcclient.managers.impl.keystone.RoleManager;
import com.yunionyun.mcp.mcclient.managers.impl.keystone.RoleAssignmentManager;
import com.yunionyun.mcp.mcclient.managers.impl.notify.NotifyManager;
import com.yunionyun.mcp.mcclient.utils.LoggerUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.slf4j.Logger;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	private static Logger logger = LoggerUtils.createLoggerFor(AppTest.class.getName());

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		Client cli = new Client("", 1000, true, true);
		try {
			TokenCredential token = cli.Authenticate("", "", "", "", "");
			Session s = cli.newSession("", null, EndpointType.PublicURL, token);
			ServerManager mgr = new ServerManager();
			logger.info("Start List");
			JSONObject srvquery = new JSONObject();
			srvquery.put("details", true);
			srvquery.put("with_meta", true);
			s.setTaskNotifyUrl("http://10.168.26.235:7777");
			ListResult result = mgr.List(s, srvquery);
			logger.info(result.toString());
			JSONObject srv = result.getDataAt(0);
			if (srv != null) {
				s.setTaskNotifyUrl("http://10.168.26.235:8888");
				String id = srv.getString("id");
				JSONObject srv2 = mgr.Get(s, id, null);
				logger.info(srv2.toJSONString());

				ServerDiskManager srvdiskman = new ServerDiskManager();
				ListResult serverdisks = srvdiskman.LisDescendent(s, id, null);
				logger.info(serverdisks.toString());
			}
			ImageManager imgman = new ImageManager();
			JSONObject imgquery = new JSONObject();
			imgquery.put("details", true);
			s.setTaskNotifyUrl("http://10.168.26.235:8888");
			ListResult imgs = imgman.List(s, imgquery);
			logger.info(imgs.toString());

			ProjectManager projman = new ProjectManager();
			JSONObject proj = projman.GetByName(s, "qiujian", null);
			RoleManager roleman = new RoleManager();
			JSONObject role = roleman.GetByName(s, "project_owner", null);
			if (role != null) {
				JSONObject rolequery = new JSONObject();
				rolequery.put("role.id", role.getString("id"));
				rolequery.put("project.id", proj.getString("id"));
				rolequery.put("effective", "true");
				rolequery.put("include_names", "true");
				RoleAssignmentManager roleassignman = new RoleAssignmentManager();
				ListResult assignments = roleassignman.List(s, rolequery);
				for (int i = 0; i < assignments.getDataSize(); i++) {
					JSONObject ass = assignments.getDataAt(i);
					System.out.print(ass);
				}
			}

			NotifyManager notifyman = new NotifyManager();
			notifyman.notify(
					s,
					"qiujian",
					NotifyManager.CONTACT_EMAIL,
					"test",
					NotifyManager.PRIORITY_NORMAL,
					"This is a normal message");

			SamlUserManager saml = new SamlUserManager();
			String uid = "admin";
			JSONObject sinfo = saml.GetSpecific(s, uid, "saml", null);
			System.out.println(sinfo);

			logger.info("Test complete!!!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Client error: " + e);
		}
	}
}
