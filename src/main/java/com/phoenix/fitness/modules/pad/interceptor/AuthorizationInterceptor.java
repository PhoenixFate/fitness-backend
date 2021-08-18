package com.phoenix.fitness.modules.pad.interceptor;

import io.jsonwebtoken.Claims;
import com.phoenix.fitness.common.constant.HttpExceptionEnum;
import com.phoenix.fitness.common.exception.HttpFitnessException;
import com.phoenix.fitness.modules.pad.utils.JwtUtils;
import com.phoenix.fitness.modules.pad.annotation.Login;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 *
 * @author Mark sm516116978@outlook.com
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;

    public static final String USER_KEY = "userId";

    public static final String GYM_KEY = "gymId";

    public static final String PARTNER_KEY = "partner";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        } else {
            return true;
        }

        if (annotation == null) {
            return true;
        }

        //获取用户凭证
        String token = request.getHeader(jwtUtils.getHeader());
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(jwtUtils.getHeader());
        }

        //凭证为空
        if (StringUtils.isBlank(token)) {
            throw new HttpFitnessException(HttpExceptionEnum.TOKEN_IS_EMPTY);
        }

        Claims claims = jwtUtils.getClaimByToken(token);
        if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())) {
            throw new HttpFitnessException(HttpExceptionEnum.TOKEN_IS_EXPIRED);
        }

        //设置userId到request里，后续根据userId，获取用户信息
        String subject = claims.getSubject();
        String[] infos = subject.split(";");
        request.setAttribute(USER_KEY, Long.parseLong(infos[0]));
        if (!StringUtils.isEmpty(infos[1])) {
            request.setAttribute(GYM_KEY, Long.parseLong(infos[1]));
        } else {
            request.setAttribute(GYM_KEY, -1);
        }
        if (!StringUtils.isEmpty(infos[2])) {
            request.setAttribute(PARTNER_KEY, Long.parseLong(infos[2]));
        } else {
            request.setAttribute(PARTNER_KEY, -1);
        }
        return true;
    }
}
