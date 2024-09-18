package springreact.practicerestapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import springreact.practicerestapi.domain.UserAccount;
import springreact.practicerestapi.services.CustomUserDetailService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    private final CustomUserDetailService customUserDetailService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, CustomUserDetailService customUserDetailService) {
        this.tokenProvider = tokenProvider;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            String jwt=getJwtFromRequest(httpServletRequest);
            if(StringUtils.hasText(jwt)&&tokenProvider.validateToken(jwt)){
                Long userId=tokenProvider.getUSerIDFromJwt(jwt);
                UserAccount userAccount=customUserDetailService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(
                        userAccount,null, Collections.emptyList()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            logger.error("Could not set User Authentication here");
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken= request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7,bearerToken.length());
        }
        return null;

    }
}
