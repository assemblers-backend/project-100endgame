package io.assemblers.project100endgame.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "custom.jwt")
public class JwtProperties {

	private final Validations validations;
	private final Secrets secrets;

	@Getter
	@RequiredArgsConstructor
	public static class Validations {
		private final Long access;
		private final Long refresh;
	}

	@Getter
	@RequiredArgsConstructor
	public static class Secrets {
		private final String appKey;
		private final String originKey;
	}

}