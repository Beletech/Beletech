package com.beletech.auth.support;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 无密码加密
 *
 * @author XueBing
 */
public class BeletechNoOpPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword.toString().equals(encodedPassword);
	}

	/**
	 * Get the singleton {@link BeletechNoOpPasswordEncoder}.
	 */
	public static PasswordEncoder getInstance() {
		return INSTANCE;
	}

	private static final PasswordEncoder INSTANCE = new BeletechNoOpPasswordEncoder();

	private BeletechNoOpPasswordEncoder() {
	}

}
