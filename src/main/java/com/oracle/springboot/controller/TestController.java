package com.oracle.springboot.controller;

import com.oracle.springboot.annotation.NoAuth;
import com.oracle.springboot.po.User;
import com.oracle.springboot.service.UserService;
import com.oracle.springboot.util.JwtUtil;
import com.oracle.springboot.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description:
 * @Author: Tu Xu
 * @CreateDate: 2022/3/24 11:33
 * @Version: 1.0
 **/
@RestController
public class TestController {
    private Logger logger= LoggerFactory.getLogger(TestController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/test")
    @NoAuth
    public ResponseVo test(int id) throws IOException {
        User user=userService.selectByPrimaryKey(id);
        System.out.println(JwtUtil.gerenateToken(user));
        ResponseVo responseVo= new ResponseVo();
        responseVo.setStatus("200");
        responseVo.setT(JwtUtil.gerenateToken(user));
        responseVo.setMessage("token创建成功");
        logger.info("用户的请求进来了");
       return responseVo;
    }
    @RequestMapping("/test1")
    public void test1(HttpServletResponse response) throws IOException {
        response.getWriter().write("我需要Token才能登录");

    }
}
