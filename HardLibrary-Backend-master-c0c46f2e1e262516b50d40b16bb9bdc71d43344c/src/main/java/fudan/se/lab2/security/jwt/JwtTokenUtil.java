package fudan.se.lab2.security.jwt;

import fudan.se.lab2.domain.Employee;
import fudan.se.lab2.service.Utility;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This is a util class to use JWT.
 * We give it to you for free. :)
 *
 * @author LBW
 */
@Component
public class JwtTokenUtil implements Serializable {
    @Resource
    private JdbcTemplate jdbcTemplate;//自动分析使用数据库
    private Utility utility;

    private static final long serialVersionUID = -3839549913040578986L;

    private JwtConfigProperties jwtConfigProperties;



    public JwtTokenUtil(JwtConfigProperties jwtConfigProperties,Utility utility) {
        this.jwtConfigProperties = jwtConfigProperties;
        this.utility = utility;
    }

    public String generateToken(Employee employee) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().addClaims(claims)
                .setSubject(employee.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfigProperties.getValidity()))
                .signWith(SignatureAlgorithm.HS512, jwtConfigProperties.getSecret()).compact();
    }

    public Employee getEmployeeFromToken(String token) {
        String username = this.getUsernameFromToken(token);
        //sql:SELECT * FROM `employee` WHERE `username` = ''
        Employee employee = utility.findEmployeeByUsername(username);
        if (validateEmployeeToken(token,employee)){
            return employee;
        }else{
            return null;
        }
    }

    public boolean validateEmployeeToken(String jwtToken, Employee employee) {
        final String username = getUsernameFromToken(jwtToken);
        return (username.equals(employee.getUsername()) && !isTokenExpired(jwtToken));
    }

    public String getUsernameFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken, Claims::getSubject);
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String username = getUsernameFromToken(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private Date getExpirationDateFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String jwtToken) {
        return Jwts.parser().setSigningKey(jwtConfigProperties.getSecret()).parseClaimsJws(jwtToken).getBody();
    }



    private boolean isTokenExpired(String jwtToken) {
        final Date expiration = getExpirationDateFromToken(jwtToken);
        return expiration.before(new Date());
    }
}

