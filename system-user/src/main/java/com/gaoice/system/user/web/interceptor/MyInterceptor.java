package com.gaoice.system.user.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截显示 /test 路径下的访问
 */
@Component
public class MyInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isHandle(request.getRequestURI())) {
            LOGGER.info("MyInterceptor-preHandle:" + request.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (isHandle(request.getRequestURI())) {
            LOGGER.info("MyInterceptor-postHandle:" + request.getRequestURI());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (isHandle(request.getRequestURI())) {
            LOGGER.info("MyInterceptor-afterCompletion:" + request.getRequestURI());
        }
    }

    private boolean isHandle(String uri) {
        String test = "/test";
        return !(uri == null
                || uri.length() < test.length()
                || !test.equals(uri.substring(0, test.length())));
    }

}
