package com.oracle.springboot.security;

import com.alibaba.fastjson.JSON;
import com.oracle.springboot.annotation.NoAuth;
import com.oracle.springboot.po.User;
import com.oracle.springboot.service.UserService;
import com.oracle.springboot.util.JwtUtil;
import com.oracle.springboot.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Description:
 * @Author: Tu Xu
 * @CreateDate: 2021/11/22 3:11
 * @Version: 1.0
 **/
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1:从request的请求头或请求行中取到jwt
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //判断目标处理器方法是否被@NoAuth注解修饰
        if (handler instanceof HandlerMethod) {
            //目标资源是springmvc当中的处理器方法
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(NoAuth.class)) {
                //目标方法处理器被@NoAuth修饰
                return true;
            } else {
                //需要验证一下token
                String token=null;
                if(null==(token=request.getHeader("token"))){
                    token=request.getParameter("token");
                }
                ResponseVo vo;
                if(token==null){
                    //该请求没携带token
                    vo=new ResponseVo("500", null, "检测不到Token,请重新登录！");
                    response.getWriter().write(JSON.toJSONString(vo));
                    return false;
                }else{
                    //token不为null
                    //token中payload位置先取到用户的身份信息

                    //失效时间
                    Date expireTime;
                    try {
                        expireTime = JwtUtil.getExpireAt(token);
                    }catch(Exception e){
                        //token不符合规范
                        vo=new ResponseVo("500", null, "jwt被篡改,请重新登录！");
                        response.getWriter().write(JSON.toJSONString(vo));
                        return false;
                    }

                    if(System.currentTimeMillis()<expireTime.getTime()){
                        //token未过期
                        String userId;
                        try {
                            userId = JwtUtil.getUserId(token);
                        }catch(Exception e){
                            //token不符合规范
                            vo=new ResponseVo("500", null, "用户身份信息被篡改,请重新登录！");
                            response.getWriter().write(JSON.toJSONString(vo));
                            return false;
                        }
                        //需要验签，拿用户的密码进行验签
                        User user=userService.selectByPrimaryKey(Integer.parseInt(userId));
                        String password=user.getPassword();

                        try {
                            JwtUtil.verify(password,token);
                            //验签成功了
                            return true;
                        }catch(Exception e){
                            //验签失败了
                            vo=new ResponseVo("500", null, "验签失败");
                            response.getWriter().write(JSON.toJSONString(vo));
                            return false;
                        }


                    }else{
                        vo=new ResponseVo("500", null, "token已失效，请重新登录！");
                        response.getWriter().write(JSON.toJSONString(vo));
                        return false;
                    }

                }
            }
        }else{
            //不是处理器方法，直接放行
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
