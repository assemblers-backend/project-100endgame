package io.assemblers.project100endgame.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		return http

			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)

			.formLogin(AbstractHttpConfigurer::disable)

			.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.authorizeHttpRequests(auth -> auth

				.requestMatchers(CorsUtils::isPreFlightRequest)
				.permitAll()

				// 인증
				.requestMatchers(HttpMethod.POST, "/api/v1/auth/login")
				.anonymous()
				.requestMatchers(HttpMethod.POST, "/api/v1/auth/refresh")
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/api/v1/auth/logout")
				.authenticated()

				// 유저
				.requestMatchers(HttpMethod.POST, "/api/v1/users/register")
				.anonymous()
				.requestMatchers(HttpMethod.GET, "/api/v1/users/me")
				.authenticated()
				.requestMatchers(HttpMethod.GET, "/api/v1/users/me/data")
				.authenticated()

				// 아이템
				.requestMatchers(HttpMethod.GET, "/api/v1/items")
				.permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/items/{id}")
				.permitAll()

				// 친구
				.requestMatchers(HttpMethod.GET, "/api/v1/users/me/friends")
				.authenticated()
				.requestMatchers(HttpMethod.GET, "/api/v1/users/me/friends/requests")
				.authenticated()
				.requestMatchers(HttpMethod.POST, "/api/v1/users/me/friends/requests")
				.authenticated()
				.requestMatchers(HttpMethod.POST, "/api/v1/users/me/friends/requests/{requestId}/accept")
				.authenticated()
				.requestMatchers(HttpMethod.POST, "/api/v1/users/me/friends/requests/{requestId}/decline")
				.authenticated()
				.requestMatchers(HttpMethod.DELETE, "/api/v1/users/me/friends/requests/{requestId}")
				.authenticated()
				.requestMatchers(HttpMethod.DELETE, "/api/v1/users/me/friends/{friendUserId}")
				.authenticated()

				// 인벤토리
				.requestMatchers(HttpMethod.GET, "/api/v1/users/me/inventory")
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/api/v1/users/me/inventory/pickup")
				.permitAll()
				.requestMatchers(HttpMethod.DELETE, "/api/v1/users/me/inventory/{itemId}/discard")
				.permitAll()

				// 프로필
				.requestMatchers(HttpMethod.GET, "/api/v1/users/me/profile")
				.authenticated()

				// 지갑
				.requestMatchers(HttpMethod.GET, "/api/v1/users/me/wallet")
				.authenticated()

				// NPC
				.requestMatchers(HttpMethod.GET, "/api/v1/npcs")
				.permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/npcs/{id}")
				.permitAll()
				.requestMatchers(
					HttpMethod.POST,
					"/api/v1/users/me/npcs/{npcId}/items/{npcItemId}/purchase"
				)
				.authenticated()
				.anyRequest()
				.authenticated()
			)

			.build();
	}
}
