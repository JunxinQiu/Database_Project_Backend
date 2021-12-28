package fudan.se.lab2.security.jwt;

import fudan.se.lab2.service.JwtUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Write your code to make this filter works.
 *
 * @author LBW
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService jwtUserDetailsService;

    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, JwtUserDetailsService jwtUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO: Implement the filter.
        //debug提示：我猜测这里会有bug，前端要把token塞到header里然后用这个方法来验证
        String tokenHeader = "Authorization";//可能会是一个bug点
        String authHeader = request.getHeader(tokenHeader);
        //存在token
        if(authHeader!=null&& !authHeader.equals("undefined")){
            String username = jwtTokenUtil.getUsernameFromToken(authHeader);
            //token中有用户名且未登录
            if(null!=username && null==SecurityContextHolder.getContext().getAuthentication()){
                //login
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                //验证user是否有效，重新设置用户对象
                if(jwtTokenUtil.validateToken(authHeader,userDetails)){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
