package com.chatting.interceptor;

import com.chatting.util.ResponseData;
import com.chatting.util.Token;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class TokenInterceptor implements HandlerInterceptor {
    @Resource
    private Token token;
    @Resource
    private ResponseData responseData;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        httpServletResponse.setCharacterEncoding("utf-8");
        String token = httpServletRequest.getParameter("token");
        if (token != null && !token.equals("")) {
            Map<String, Object> user = this.token.verify(token);
            if (user != null) {
                httpServletRequest.setAttribute("uuid", user.get("uuid"));
                httpServletRequest.setAttribute("username", user.get("username"));
                return true;
            }
        }
        httpServletResponse.getWriter().write(responseData.assembleCallBack(401, "please login in", null));
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
