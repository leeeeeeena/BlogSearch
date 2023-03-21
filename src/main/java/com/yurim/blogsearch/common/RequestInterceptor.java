package com.yurim.blogsearch.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            if (((HandlerMethod) handler).getBean() instanceof BasicErrorController) {
                return true;
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            log.debug("tid{}[preHandle] {} : request url :{} / method : {}", Thread.currentThread().getId(),
                    handlerMethod.getBeanType().getName(), request.getRequestURI(), request.getMethod());
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Unloggable Handler is mapped in {}", getClass().getName());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if(handler instanceof HandlerMethod) {
            if (((HandlerMethod) handler).getBean() instanceof BasicErrorController) {
                return;
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            log.debug("tid{}[postHandle] {} : getStatus={}", Thread.currentThread().getId(),
                    handlerMethod.getBeanType().getName(), response.getStatus());
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Unloggable Handler is mapped in {}", getClass().getName());
            }
        }
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object object,
            Exception ex
    ) throws Exception {
        log.debug("tid{}[afterCompletion]",Thread.currentThread().getId());
    }
}

