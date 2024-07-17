package sukhrob.developer.rest_api.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sukhrob.developer.rest_api.payload.TokenExpiredException;

import java.util.Date;
import java.util.UUID;

@Component
public class JWTProvider {

    @Value(value = "${accessTokenLifeTime}")
    private Long accessTokenLifeTime;

    @Value(value = "${refreshTokenLifeTime}")
    private Long refreshTokenLifeTime;

    @Value(value = "${tokenSecretKey}")
    private String secretKey;

    public String generateTokenFromUUID(UUID id, boolean isAccess) {
        Date expiredDate = new Date(System.currentTimeMillis() + (isAccess ? accessTokenLifeTime : refreshTokenLifeTime));
        return "Bearer " + Jwts
                .builder()
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .setSubject(id.toString())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getIdFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException();
        }
    }

    public void validateToken(String token) {
        Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
    }

}
