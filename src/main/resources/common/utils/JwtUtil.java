package com.black.common.utils;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2019/4/25 11:46
 * @atuther net source
 */
public class JwtUtil {

    //密钥
    public static final String SECRET = "***xbsa-yutang-fjafj";
    //过期时间:秒
    public static final int EXPIRE = 60 * 60;

    /**
     * 生成Token
     * @param
     * @return
     * @throws Exception
     */
    public static String createToken(Long userId,String userName){
        try {
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.SECOND, EXPIRE);
            Date expireDate = nowTime.getTime();
            Map<String, Object> map = new HashMap<>();
            map.put("alg", "HS256");
            map.put("typ", "JWT");

            return JWT.create()
                    .withHeader(map)//头
                    .withClaim("userId", userId)
                    .withClaim("userName", userName)
                    .withSubject("密钥")//
                    .withIssuedAt(new Date())//签名时间
                    .withExpiresAt(expireDate)//过期时间
                    .sign(Algorithm.HMAC256(SECRET));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证Token
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token)throws Exception{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        }catch (Exception e){
            throw new RuntimeException("凭证已过期，请重新登录");
        }
        return jwt.getClaims();
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public static Map<String, Claim> parseToken(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaims();
    }

    public static Long userIdByReq(HttpServletRequest httpServletRequest){
       return Long.parseLong(parseToken(httpServletRequest.getHeader("Token")).get("userId").toString());
    }

    public static String userNameByReq(HttpServletRequest httpServletRequest){
        return parseToken(httpServletRequest.getHeader("Token")).get("userName").asString();
    }

}
