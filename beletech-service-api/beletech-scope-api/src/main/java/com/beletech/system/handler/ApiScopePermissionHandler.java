package com.beletech.system.handler;

import com.beletech.core.secure.BeletechUser;
import com.beletech.core.secure.handler.IPermissionHandler;
import com.beletech.core.secure.utils.AuthUtil;
import com.beletech.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.beletech.system.cache.ApiScopeCache.permissionCode;
import static com.beletech.system.cache.ApiScopeCache.permissionPath;

/**
 * 接口权限校验类
 *
 * @author XueBing
 */
public class ApiScopePermissionHandler implements IPermissionHandler {

	@Override
	public boolean permissionAll() {
		HttpServletRequest request = WebUtil.getRequest();
		BeletechUser user = AuthUtil.getUser();
		if (request == null || user == null) {
			return false;
		}
		String uri = request.getRequestURI();
		List<String> paths = permissionPath(user.getRoleId());
		if (paths == null || paths.size() == 0) {
			return false;
		}
		return paths.stream().anyMatch(uri::contains);
	}

	@Override
	public boolean hasPermission(String permission) {
		HttpServletRequest request = WebUtil.getRequest();
		BeletechUser user = AuthUtil.getUser();
		if (request == null || user == null) {
			return false;
		}
		List<String> codes = permissionCode(permission, user.getRoleId());
		return codes != null && codes.size() != 0;
	}

}
