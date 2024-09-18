package springreact.practicerestapi.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import springreact.practicerestapi.domain.UserAccount;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication){
        UserAccount user= (UserAccount) authentication.getPrincipal();
        Date now= new Date(System.currentTimeMillis());
        Date expiry= new Date(now.getTime()+30_000000);
        String userID=Long.toString(user.getId());
        Map<String,Object> claims= new HashMap<>();
        claims.put("id",(Long.toString(user.getId())));
        claims.put("username",user.getUsername());
        claims.put("fullname",user.getFullName());
        return Jwts.builder()
                .setSubject(userID)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512,"SecretKeyToGenJWTs")
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey("SecretKeyToGenJWTs").parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException e){
            System.out.println("Invalid JWT token");
        }catch (ExpiredJwtException e){
            System.out.println("Expired JWT token");
        }catch (UnsupportedJwtException e){
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException e){
            System.out.println("JWT claims string is empty");
        }
        return  false;
    }

    public Long getUSerIDFromJwt(String token){
        Claims claims=Jwts.parser().setSigningKey("SecretKeyToGenJWTs").parseClaimsJws(token).getBody();
        String id= (String) claims.get("id");
        return  Long.parseLong(id);
    }
}
