package com.plateer.config;

import com.plateer.error.UnauthorizedException;
import com.plateer.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final String HEADER_AUTH = "Authorization";

    private JwtService jwtService;

    public JwtInterceptor(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("JWT 인터셉터!!!!!!!!!!!!!");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if(request.getHeader(HEADER_AUTH) != null){
            final String token = request.getHeader(HEADER_AUTH).split(" ")[1];
            if(token == null || !jwtService.isUsable(token)){
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("Msg","토큰만료");
            }
        }

        return true;
    }
}
