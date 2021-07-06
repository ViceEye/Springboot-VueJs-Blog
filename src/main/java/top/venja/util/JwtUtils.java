package top.venja.util;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import top.venja.common.lang.Result;
import top.venja.entity.User;
import top.venja.service.UserService;

import java.util.Date;

/**
 * jwt工具类
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "vueblog.jwt")
public class JwtUtils {

    @Autowired
    UserService userService;

    private String secret = "985169151";
    private String header;

    /**
     * 生成jwt token
     */
    public String generateToken(long userId, String passToken) {
        Date createDate = new Date();

        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(userId+"")
                .setIssuedAt(createDate)
                .setAudience(passToken)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            log.debug("validate is token error ", e);
            return null;
        }
    }

    public boolean isTokenNotValid(Claims claims) {
        if (claims != null) {
            User user = userService.getOne(new QueryWrapper<User>().eq("id", claims.getSubject()));
            if (user == null)
                return false;
            return !(user.getPassToken().equals(claims.getAudience()));
        }
        return true;
    }

}
