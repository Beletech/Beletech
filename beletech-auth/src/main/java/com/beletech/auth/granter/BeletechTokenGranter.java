package com.beletech.auth.granter;

import com.beletech.core.redis.cache.BeletechRedis;
import com.beletech.core.social.props.SocialProperties;
import com.beletech.system.user.feign.IUserClient;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义拓展TokenGranter
 *
 * @author XueBing
 */
public class BeletechTokenGranter {

	/**
	 * 自定义tokenGranter
	 */
	public static TokenGranter getTokenGranter(final AuthenticationManager authenticationManager, final AuthorizationServerEndpointsConfigurer endpoints, BeletechRedis beletechRedis, IUserClient userClient, SocialProperties socialProperties) {
		// 默认tokenGranter集合
		List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
		// 增加验证码模式
		granters.add(new CaptchaTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), beletechRedis));
		// 增加第三方登陆模式
		granters.add(new SocialTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), userClient, socialProperties));
		// 组合tokenGranter集合
		return new CompositeTokenGranter(granters);
	}

}
