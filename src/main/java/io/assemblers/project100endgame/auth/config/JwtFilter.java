package io.assemblers.project100endgame.auth.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.assemblers.project100endgame.auth.service.TokenProvider;
import io.assemblers.project100endgame.auth.service.TokenService;
import io.assemblers.project100endgame.user.dto.UsersDetails;
import io.assemblers.project100endgame.user.repository.UsersRepository;
import io.assemblers.project100endgame.user.service.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final UsersRepository usersRepository;
	private final UsersService usersService;
	private final TokenProvider tokenProvider;
	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		String token = tokenService.resolveToken(request);

		if ( token != null && tokenProvider.validate(token) && tokenProvider.parseClaims(token).get("isAccess").toString().equals("true")) {
			try {
				Long tokenId = Long.valueOf(tokenProvider.parseClaims(token).getSubject());

				UsersDetails usersDetails = usersService.loadUserById(tokenId);

				Authentication authentication = new UsernamePasswordAuthenticationToken(
					usersDetails,
					null,
					usersDetails.getAuthorities()
				);

				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				log.warn("인증 요청 실패: {}", e.getMessage());
			}
		}

		filterChain.doFilter(request, response);
	}
}
