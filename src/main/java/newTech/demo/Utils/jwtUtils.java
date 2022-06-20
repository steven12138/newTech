package newTech.demo.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import newTech.demo.Module.Data.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class jwtUtils {
    private static final long expired_time = 1000 * 60 * 60 * 2;
    @Value("${token}")
    private String secret;
    private String header;

    public long getExpired_time() {
        return expired_time;
    }

    public String generateToken(Account user) {
        Map<String, Object> payload = new HashMap<>(6);
        payload.put("id", user.getId());
        payload.put("username", user.getUsername());
        payload.put("is_tech", user.getIs_tech());
        payload.put("is_phy", user.getIs_phy());
        payload.put("is_admin", user.is_admin());
        payload.put("sid", user.getSid());
        payload.put("eid", user.getEid());
        payload.put("real_name", user.getRealName());

        Date expirationDate = new Date(System.currentTimeMillis() + expired_time);

        return Jwts.builder()
                .addClaims(payload)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getTokenInfo(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getId(String token) {
        try {
            Claims payload = getTokenInfo(token);
            return (Integer) payload.get("id");
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = getTokenInfo(token);
            System.out.println(claims);
            System.out.println(token);
            Date expiration = claims.getExpiration();
            System.out.println(expiration);
            return expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
