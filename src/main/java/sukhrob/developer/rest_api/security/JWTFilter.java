package sukhrob.developer.rest_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sukhrob.developer.rest_api.exception.RestException;
import sukhrob.developer.rest_api.services.AuthService;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    public JWTFilter(AuthService authService, PasswordEncoder passwordEncoder, JWTProvider jwtProvider) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.substring(7);
            String userId = jwtProvider.getIdFromToken(token);
            UserDetails userDetails = authService.loadById(UUID.fromString(userId));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else if (authorization != null && authorization.startsWith("Basic")){
            String token = authorization.substring("Basic ".length());
            String basic = new String(Base64.getDecoder().decode(token));
            String[] split = basic.split(":");
            UserDetails userDetails = authService.loadUserByUsername(split[0]);
            boolean matches = passwordEncoder.matches(split[1], userDetails.getPassword());
            if (!matches)
                throw new RestException(HttpStatus.UNAUTHORIZED, "Login yoki parol xato");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
