package co.g2academy.tokomurah.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author ifnub
 */
@Component
public class JwtTokenUtil {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000; //5 Hours
    @Value("${jwt.secret}")
    private String secret;
    private Claims getAllClaimsFromToken(String token) throws ExpiredJwtException{
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody();
    }
    public String getUsernameFromToken(String token) throws ExpiredJwtException{
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }
    private Date getExpirationDateFromToken(String token){
        Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration();
    }
    private Boolean isTokenExpired(String token){
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
}
