package com.gotostudy.study.com.utils;

import com.gotostudy.study.com.utils.resultutil.R;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author helen
 * @since 2019/10/16
 */
public class JwtUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;//设置token的过期时间
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";//密钥，对字符进行加密

    public static String getJwtToken(String id, String nickname){

        return Jwts.builder()
                //设置字符串的头信息，不需要改动
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //设置过期的时间，guli-user随便写
                .setSubject("gotostudy-user")
                .setIssuedAt(new Date())//当前时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))//当前时间加上有效期
                //设置token的主题部分，存储主体信息
                .claim("id", id)//id和nickname随意
                .claim("nickname", nickname)//可以多个.claim("nickname", nickname)追加即可
                //最终按照HS256进行编码
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    /**
     * 判断token是否存在与有效，判断token是否伪造
     */
    public static boolean checkTokenByToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {//根据密钥进行验证
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效，判断token是否伪造
     */
    public static boolean checkTokenByRequest(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) {
                return false;
            }//根据密钥进行验证
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token字符串获取会员id
     */
    public static String getUsrIdByRequest(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) {
            return "";
        }

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}
