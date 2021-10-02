package sunflower.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class CuzJwt {

    //过期时间
    private long expire;
    //密钥
    private String secret;
    //令牌的名称
    private String header;

    // 根据登录输入的用户名,生成相关的JWT令牌
    public String generatorJWT(String username) {

        Date nowDate = new Date();
        Date expDate = new Date(nowDate.getTime() + 1000 * expire);

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS512");
        map.put("typ", "JWT");

        return Jwts.builder()
                .setHeaderParams(map) //头参数
                .setSubject(username)  // JWT主体
                .setIssuedAt(nowDate) // 签发时间
                .setExpiration(expDate) //过期时间
                .signWith(SignatureAlgorithm.HS512, secret)  // 设置签名(加密方式和密钥)
                .compact(); // 根据以上信息生成JWT字符串令牌
    }

    // 根据登录信息, 解析JWT令牌
    public Claims parseJWT(String token) {
        // try : 防止伪造的token进来后解析过程中出现异常
        try {

            return Jwts.parser()
                    .setSigningKey(secret) // 设置解析用的密钥
                    .parseClaimsJwt(token) // 解析Cliams部分
                    .getBody(); // 返回


        } catch (Exception e) {
            System.out.println("JWT令牌错误");
            return null;
        }

    }

    // 检查JWT是否过期
    public boolean isExpireJwt(Claims claims) {
        // 获得主体部分的expiration日期,和现在进行比对
        return claims.getExpiration().before(new Date());
    }
}
