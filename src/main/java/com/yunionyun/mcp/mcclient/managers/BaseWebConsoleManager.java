package com.yunionyun.mcp.mcclient.managers;

import com.yunionyun.mcp.mcclient.EndpointType;

/**
 * @author zxc
 * @date 2020/01/19
 */
public class BaseWebConsoleManager extends ResourceManager {
	/**
	 * @param keyword
	 * @param keywordPlural
	 * @param endpointType  EndpointType.InternalURL
	 * @param columns
	 * @param adminColumns
	 */
	public BaseWebConsoleManager(
			String keyword,
			String keywordPlural,
			EndpointType endpointType,
			String[] columns,
			String[] adminColumns) {
		super("webconsole", endpointType, null, columns, adminColumns, keyword, keywordPlural, null);
	}
}
