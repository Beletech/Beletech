package com.beletech.auth.support;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义密码工厂
 *
 * @author Rob
 * @since 5.0
 */
public class BeletechPasswordEncoderFactories {

	/**
	 * Creates a {@link DelegatingPasswordEncoder} with default mappings. Additional
	 * mappings may be added and the encoding will be updated to conform with best
	 * practices. However, due to the nature of {@link DelegatingPasswordEncoder} the
	 * updates should not impact users. The mappings current are:
	 *
	 * <ul>
	 * <li>beletech - {@link BeletechPasswordEncoder} (sha1(md5("password")))</li>
	 * <li>bcrypt - {@link BCryptPasswordEncoder} (Also used for encoding)</li>
	 * <li>noop - {@link BeletechNoOpPasswordEncoder}</li>
	 * <li>pbkdf2 - {@link Pbkdf2PasswordEncoder}</li>
	 * <li>scrypt - {@link SCryptPasswordEncoder}</li>
	 * </ul>
	 *
	 * @return the {@link PasswordEncoder} to use
	 */
	public static PasswordEncoder createDelegatingPasswordEncoder() {
		String encodingId = "beletech";
		Map<String, PasswordEncoder> encoders = new HashMap<>(16);
		encoders.put(encodingId, new BeletechPasswordEncoder());
		encoders.put("bcrypt", new BCryptPasswordEncoder());
		encoders.put("noop", BeletechNoOpPasswordEncoder.getInstance());
		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
		encoders.put("scrypt", new SCryptPasswordEncoder());

		return new DelegatingPasswordEncoder(encodingId, encoders);
	}

	private BeletechPasswordEncoderFactories() {
	}

}
