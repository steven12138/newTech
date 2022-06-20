package newTech.demo.Filter;

import io.jsonwebtoken.Claims;
import newTech.demo.Utils.jwtUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Resource
    private jwtUtils jwtUtils;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization_header = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization_header)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization_header.split(" ")[1];
        Claims userInfo = jwtUtils.getTokenInfo(token);
        if (Objects.isNull(userInfo)) {
            filterChain.doFilter(request, response);
            return;
        }

        String redisKey = "login" + userInfo.get("id");
        String target_token = (String) redisTemplate.opsForValue().get(redisKey);

        if (!Objects.equals(target_token, token)) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean is_admin = (boolean) userInfo.get("is_admin");
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(is_admin ? "admin" : "student");
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(simpleGrantedAuthority);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userInfo, null, authorityList));
        filterChain.doFilter(request, response);
    }
}
