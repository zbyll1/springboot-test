package com.oracle.springboot.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.oracle.springboot.po.User;

import java.util.Date;
import java.util.List;

/**
 head.payload.sign632
 43hsfhsdhfkdsf.973hdskfdskgfdskgk.hfdsds9743297
 {"算法类型":""}    身份信息 {"userId":2}   用户的密码+相关信息加密之后作为签名
 **/
public class JwtUtil {

    public static long expireTime=30*24*60*60*1000L;

    public static String gerenateToken(User user){
        String token="";
        /** jwt  header 加密的算法 类型
         *       payload  有效信息(用户的身份信息)
         *       signature 签名
         *
         */

        //使用jwt创建一个jwt,将用户的id存到payload中，使用用户自己的密码作为秘钥，使用HMAC256进行签名
        long now= System.currentTimeMillis();
        token= JWT.create().withAudience(user.getId()+"").withExpiresAt(new Date(now+expireTime))// 将 user id 保存到 token 里面
                .sign(Algorithm.HMAC256(user.getPassword()));// 以 password 作为 token 的密钥
        //eyJpc3MiOiJKb2huI.eyJpc3MiOiJ.Kb2huIFd1IEp
        return token;
    }
   //获取用户的id
    public static String getUserId(String token){
        ////eyJpc3MiOiJKb2huI.eyJpc3MiOiJ.Kb2huIFd1IEp
        //{""}.{"userID":2,"menus":{}}.{""}
        List<String> list=JWT.decode(token).getAudience();
        return list.get(0);
    }
    //从jwt当中获取token的失效时间
    public static Date getExpireAt(String token){
        return JWT.decode(token).getExpiresAt();
    }
  //验签
    public static boolean verify(String password,String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(password)).build();
        jwtVerifier.verify(token);
        return true;

    }

}
