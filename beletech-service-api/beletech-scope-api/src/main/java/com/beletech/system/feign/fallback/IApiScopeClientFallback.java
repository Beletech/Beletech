package com.beletech.system.feign.fallback;

import com.beletech.system.feign.IApiScopeClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * IApiScopeClientFallback
 *
 * @author XueBing
 */
@Component
public class IApiScopeClientFallback implements IApiScopeClient {
	@Override
	public List<String> permissionPath(String roleId) {
		return null;
	}

	@Override
	public List<String> permissionCode(String permission, String roleId) {
		return null;
	}
}
