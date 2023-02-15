package com.tophyuk.board.handler;

import com.tophyuk.board.domain.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        clearAuthenticationAttributes(request);
        redirectStrategy(request, response, authentication);
    }

    // 이전에 로그인 실패한 이력이 있을 경우 session에 남아있는 실패 이력 제거
    public void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            httpSession.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

    // 리다이랙션 전술
    // 1. 바로 로그인 페이지로 와서 성공하면 -> /main
    // 2. 인증이 필요한 페이지에 미인증 상태에서 접근해서 로그인으로 팅긴경우 -> 해당 권한에 따른 로그인 화면으로 전송
    public void redirectStrategy(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            redirectStrategy.sendRedirect(request, response, "/main");
        } else {
            // 중요! 인증이 필요한 페이지에 접근시 미인증 상태여서 로그인 페이지로 보낼 경우
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            if (roles.contains(Role.USER.getKey())) {
                redirectStrategy.sendRedirect(request, response, "/");
            } else if(roles.contains(Role.ADMIN.getKey())) {
                redirectStrategy.sendRedirect(request, response, "/admin");
            }
        }

    }
}
