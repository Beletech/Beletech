package com.beletech.auth.endpoint;

import com.beletech.core.secure.BeletechUser;
import com.wf.captcha.SpecCaptcha;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.beletech.common.cache.CacheNames;
import com.beletech.core.cache.utils.CacheUtil;
import com.beletech.core.jwt.JwtUtil;
import com.beletech.core.jwt.props.JwtProperties;
import com.beletech.core.launch.constant.TokenConstant;
import com.beletech.core.redis.cache.BeletechRedis;
import com.beletech.core.secure.utils.AuthUtil;
import com.beletech.core.tenant.annotation.NonDS;
import com.beletech.core.tool.api.Result;
import com.beletech.core.tool.support.Kv;
import com.beletech.core.tool.utils.StringUtil;
import com.beletech.core.tool.utils.WebUtil;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import static com.beletech.core.cache.constant.CacheConstant.*;

/**
 * BeletechEndPoint
 *
 * @author XueBing
 */
@NonDS
@Slf4j
@RestController
@AllArgsConstructor
public class BeletechTokenEndPoint {

	private final BeletechRedis beletechRedis;
	private final JwtProperties jwtProperties;

	@GetMapping("/oauth/user-info")
	public Result<Authentication> currentUser(Authentication authentication) {
		return Result.data(authentication);
	}

	@GetMapping("/oauth/captcha")
	public Kv captcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
		String verCode = specCaptcha.text().toLowerCase();
		String key = StringUtil.randomUUID();
		// 存入redis并设置过期时间为30分钟
		beletechRedis.setEx(CacheNames.CAPTCHA_KEY + key, verCode, Duration.ofMinutes(30));
		// 将key和base64返回给前端
		return Kv.create().set("key", key).set("image", specCaptcha.toBase64());
	}

	@GetMapping("/oauth/logout")
	public Kv logout() {
		BeletechUser user = AuthUtil.getUser();
		if (user != null && jwtProperties.getState()) {
			String token = JwtUtil.getToken(WebUtil.getRequest().getHeader(TokenConstant.HEADER));
			JwtUtil.removeAccessToken(user.getTenantId(), String.valueOf(user.getUserId()), token);
		}
		return Kv.create().set("success", "true").set("msg", "success");
	}

	@GetMapping("/oauth/clear-cache")
	public Kv clearCache() {
		CacheUtil.clear(BIZ_CACHE);
		CacheUtil.clear(USER_CACHE);
		CacheUtil.clear(DICT_CACHE);
		CacheUtil.clear(FLOW_CACHE);
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(PARAM_CACHE);
		CacheUtil.clear(RESOURCE_CACHE);
		CacheUtil.clear(MENU_CACHE);
		CacheUtil.clear(DICT_CACHE, Boolean.FALSE);
		CacheUtil.clear(MENU_CACHE, Boolean.FALSE);
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		CacheUtil.clear(PARAM_CACHE, Boolean.FALSE);
		return Kv.create().set("success", "true").set("msg", "success");
	}

}
