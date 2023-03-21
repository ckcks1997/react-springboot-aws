package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class RedirectUrlCookieFilter extends OncePerRequestFilter {
    public static final String REDIRECT_URI_PARAM = "redirect_url";
    private static final int MAX_AGE = 180;

    //get 파라미터의 redirect 경로를 쿠키에 저장하는 코드, webSecurityConfig에 추가
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().startsWith("/auth/authorize")){
            try{
                log.info("request url {}", request.getRequestURI());
                String redirectUrl = request.getParameter(REDIRECT_URI_PARAM);

                Cookie cookie = new Cookie(REDIRECT_URI_PARAM, redirectUrl);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(MAX_AGE);
                response.addCookie(cookie);
            }catch (Exception e){
                logger.error("could not set user authentication in security context", e);
                log.info("unauthorized request!");
            }
        }
        filterChain.doFilter(request, response);
    }
}
