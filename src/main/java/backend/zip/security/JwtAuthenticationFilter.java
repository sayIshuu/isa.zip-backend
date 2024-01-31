package backend.zip.security;

import backend.zip.domain.auth.RefreshToken;
import backend.zip.domain.auth.Token;
import backend.zip.domain.auth.TokenInfo;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.global.exception.token.CustomMalformedJwtException;
import backend.zip.global.exception.token.NotMatchedTokenException;
import backend.zip.global.exception.token.TokenException;
import backend.zip.global.exception.token.TokenNotFoundException;
import backend.zip.service.RefreshTokenRedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static backend.zip.domain.auth.TokenType.ACCESS;
import static backend.zip.domain.auth.TokenType.REFRESH;
import static backend.zip.global.status.ErrorStatus.*;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    private final String UTF_8 = "utf-8";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            Token token = resolveToken(request);

            if (token != null && jwtTokenProvider.validateToken(token.getToken())) {
                if (token.getTokenType() == REFRESH) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(token.getToken());
                    RefreshToken originRefreshToken = refreshTokenRedisService.getRefreshToken(Long.parseLong(authentication.getName()));

                    if (originRefreshToken == null) {
                        throw new TokenNotFoundException(TOKEN_NOTFOUND);
                    }

                    if (token.getToken().equals(originRefreshToken.getRefreshToken())) {
                        TokenInfo tokenInfo = reissueTokenAndSaveOnRedis(authentication);
                        makeTokenInfoResponse(response, tokenInfo);
                        return;
                    }
                    throw new NotMatchedTokenException(NOT_MATCHED_TOKEN);
                }
                Authentication authentication = jwtTokenProvider.getAuthentication(token.getToken());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (TokenException e) {
            makeTokenExceptionResponse(response, e);
        }
    }

    // Http 요청에서 토큰 추출
    public Token resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(StringUtils.hasText(token)) {
            if(token.startsWith("Bearer") && token.length() > 7) {
                int tokenStartIndex = 7;
                return Token.builder()
                        .tokenType(ACCESS)
                        .token(token.substring(tokenStartIndex))
                        .build();
            }
            throw new CustomMalformedJwtException(MALFORMED_HEADER);
        }

        token = request.getHeader("refreshToken");
        if(StringUtils.hasText(token)) {
            return Token.builder()
                    .tokenType(REFRESH)
                    .token(token)
                    .build();
        }
        return null;
    }

    // 토큰 재발급
    private TokenInfo reissueTokenAndSaveOnRedis(Authentication authentication) {
        TokenInfo tokenInfo = jwtTokenProvider.generationToken(authentication);
        refreshTokenRedisService.saveRefreshToken(Long.parseLong(authentication.getName()), tokenInfo.getRefreshToken());
        return tokenInfo;
    }

    private void makeTokenInfoResponse(HttpServletResponse response, TokenInfo tokenInfo) throws IOException {
        String tokenReissuedMessage = "토큰 재발급 완료";

        response.setStatus(HttpStatus.CREATED.value());
        response.setCharacterEncoding(UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(
                objectMapper.writeValueAsString(
                        ApiResponse.onSuccess(tokenReissuedMessage, tokenInfo)
                )
        );
    }

    private void makeTokenExceptionResponse(HttpServletResponse response, TokenException e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(
                objectMapper.writeValueAsString(
                        ApiResponse.onFailure(e.getBaseErrorCode().toString(), e.getMessage(), null)
                )
        );
    }
}
