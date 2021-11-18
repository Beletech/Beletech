package com.beletech.auth.support;

import com.beletech.core.launch.constant.TokenConstant;
import lombok.AllArgsConstructor;
import com.beletech.auth.service.BeletechUserDetails;
import com.beletech.auth.utils.TokenUtil;
import com.beletech.core.jwt.JwtUtil;
import com.beletech.core.jwt.props.JwtProperties;
import com.beletech.core.tool.utils.Func;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt返回参数增强
 *
 * @author XueBing
 */
@AllArgsConstructor
public class BeletechJwtTokenEnhancer implements TokenEnhancer {

	private final JwtAccessTokenConverter jwtAccessTokenConverter;
	private final JwtProperties jwtProperties;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		BeletechUserDetails principal = (BeletechUserDetails) authentication.getUserAuthentication().getPrincipal();

		//token参数增强
		Map<String, Object> info = new HashMap<>(16);
		info.put(TokenUtil.CLIENT_ID, TokenUtil.getClientIdFromHeader());
		info.put(TokenUtil.USER_ID, Func.toStr(principal.getUserId()));
		info.put(TokenUtil.DEPT_ID, Func.toStr(principal.getDeptId()));
		info.put(TokenUtil.POST_ID, Func.toStr(principal.getPostId()));
		info.put(TokenUtil.ROLE_ID, Func.toStr(principal.getRoleId()));
		info.put(TokenUtil.TENANT_ID, principal.getTenantId());
		info.put(TokenUtil.OAUTH_ID, principal.getOauthId());
		info.put(TokenUtil.ACCOUNT, principal.getAccount());
		info.put(TokenUtil.USER_NAME, principal.getUsername());
		info.put(TokenUtil.NICK_NAME, principal.getName());
		info.put(TokenUtil.REAL_NAME, principal.getRealName());
		info.put(TokenUtil.ROLE_NAME, principal.getRoleName());
		info.put(TokenUtil.AVATAR, principal.getAvatar());
		info.put(TokenUtil.DETAIL, principal.getDetail());
		info.put(TokenUtil.LICENSE, TokenUtil.LICENSE_NAME);

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

		//token状态设置
		if (jwtProperties.getState()) {
			OAuth2AccessToken oAuth2AccessToken = jwtAccessTokenConverter.enhance(accessToken, authentication);
			saveTokenInfos(accessToken, principal, oAuth2AccessToken.getValue());
		}

		return accessToken;
	}

	private void saveTokenInfos(OAuth2AccessToken accessToken, BeletechUserDetails principal, String tokenValue) {
		String tenantId = principal.getTenantId();
		String userId = Func.toStr(principal.getUserId());
		Map<String,Object> tokenInfos = new HashMap(20);
		tokenInfos.put(TokenUtil.AVATAR, principal.getAvatar());
		tokenInfos.put(TokenUtil.DETAIL, principal.getDetail());
		tokenInfos.put(TokenConstant.ACCESS_TOKEN, tokenValue);
		tokenInfos.put(TokenConstant.EXPIRES_IN, accessToken.getExpiresIn());
		JwtUtil.addAccessToken(tenantId, userId, tokenInfos, accessToken.getExpiresIn());
	}
}
