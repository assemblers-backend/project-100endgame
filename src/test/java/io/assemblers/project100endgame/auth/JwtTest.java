package io.assemblers.project100endgame.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import io.assemblers.project100endgame.auth.config.properties.JwtProperties;
import io.assemblers.project100endgame.auth.dto.TokenPair;
import io.assemblers.project100endgame.auth.service.TokenProvider;
import io.jsonwebtoken.JwtException;

public class JwtTest {
	final String SECRET_KEY_STR = "6rG06rCV67CV7IiY7Ked7Ked7LKg7LCM65Ok64yA6rCA66as7YSw65yo66Ck";
	TokenProvider tokenProvider;
	JwtProperties jwtProperties;

	@BeforeEach
	void setTokenProvider() {
		jwtProperties = new JwtProperties(
			new JwtProperties.Validations(3600000L, 3600000L),
			new JwtProperties.Secrets(SECRET_KEY_STR, SECRET_KEY_STR)
		);
		tokenProvider = new TokenProvider(jwtProperties);
	}

	@Test
	void 토큰_발급_테스트() {
		// 정상적으로 발급된 토큰이 발급되었다면
		TokenPair tokenPair = tokenProvider.issueTokenPair(998244353L);

		String accessToken = tokenPair.accessToken();
		String refreshToken = tokenPair.refreshToken();

		//이를 검증하면 정상적으로 나와야 한다
		assertThat(tokenProvider.validate(accessToken)).isTrue();
		assertThat(tokenProvider.validate(refreshToken)).isTrue();

		// ID를 파싱하면 정상적으로 나와야 한다
		assertThat(tokenProvider.parseId(accessToken)).isEqualTo(998244353L);
		assertThat(tokenProvider.parseId(refreshToken)).isEqualTo(998244353L);
	}

	@Test
	void 만료된_토큰_테스트() {
		// 토큰이 만료되었다면
		jwtProperties = new JwtProperties(
			new JwtProperties.Validations(-3600000L, -3600000L),
			new JwtProperties.Secrets(SECRET_KEY_STR, SECRET_KEY_STR)
		);
		tokenProvider = new TokenProvider(jwtProperties);

		TokenPair tokenPair = tokenProvider.issueTokenPair(998244353L);

		String accessToken = tokenPair.accessToken();
		String refreshToken = tokenPair.refreshToken();

		// 검증 실패가 되어야 한다.
		assertThat(tokenProvider.validate(accessToken)).isFalse();
		assertThat(tokenProvider.validate(refreshToken)).isFalse();

		// 오류는 JwtException을 던져야 하고 만료되었다는 메세지가 나와야 한다.
		assertThatThrownBy(() -> tokenProvider.parseId(accessToken))
			.isInstanceOf(JwtException.class)
			.hasMessageContaining("JWT expired");

		assertThatThrownBy(() -> tokenProvider.parseId(refreshToken))
			.isInstanceOf(JwtException.class)
			.hasMessageContaining("JWT expired");
	}

	@Test
	void 올바르지_않은_토큰_테스트() {
		// 올바르지 않은 토큰이 들어왔다면

		final String WRONG_SECRET_KEY_STR = "65Kk7JaR7IaQ65S46rmN7LKg6raM7J2E7KCV7IOB7ZmU7ZW0";

		JwtProperties wrongJwtProperties = new JwtProperties(
			new JwtProperties.Validations(3600000L, 3600000L),
			new JwtProperties.Secrets(WRONG_SECRET_KEY_STR, WRONG_SECRET_KEY_STR)
		);

		TokenProvider wrongTokenProvider = new TokenProvider(wrongJwtProperties);

		TokenPair tokenPair = wrongTokenProvider.issueTokenPair(998244353L);

		String accessToken = tokenPair.accessToken();
		String refreshToken = tokenPair.refreshToken();

		// 검증 실패가 되어야 한다.
		assertThat(tokenProvider.validate(accessToken)).isFalse();
		assertThat(tokenProvider.validate(refreshToken)).isFalse();

		// 오류는 JwtException을 던져야 하고 키가 맞지 않는다는 메세지가 나와야 한다.
		assertThatThrownBy(() -> tokenProvider.parseId(accessToken))
			.isInstanceOf(JwtException.class)
			.hasMessageContaining("JWT signature does not match locally computed signature.")
		;

		assertThatThrownBy(() -> tokenProvider.parseId(refreshToken))
			.isInstanceOf(JwtException.class)
			.hasMessageContaining("JWT signature does not match locally computed signature.")
		;
	}
}
