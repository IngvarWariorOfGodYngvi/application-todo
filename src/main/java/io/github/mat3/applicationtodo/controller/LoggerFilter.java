package io.github.mat3.applicationtodo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
class LoggerFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest){
            var httpServletRequest = (HttpServletRequest) request;
            logger.info("[doFilter] " + httpServletRequest.getMethod() + " " +httpServletRequest.getRequestURI());
        }
        //jeśli nie dam chain to filtrowanie popsuje działanie programu
        chain.doFilter(request,response);
    }

}
