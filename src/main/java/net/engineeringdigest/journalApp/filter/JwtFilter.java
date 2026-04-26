package net.engineeringdigest.journalApp.filter;

import net.engineeringdigest.journalApp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService ;

    @Autowired
    private JWTUtil jwtUtil ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String userName = null ;
        String jwt = null ;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            userName = jwtUtil.extractUsername(jwt);
        }
        if(userName != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if (jwtUtil.validateToken(jwt)){   // check token validation
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null , userDetails.getAuthorities()); // created auth object giving details
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // set details of auth
                SecurityContextHolder.getContext().setAuthentication(auth); // set auth in the security context holder
            }
        }
        response.addHeader("User","Ram"); // response mei header added
        filterChain.doFilter(request,response); // chain mai aage bhej diya request & reponse if any other more filters exists down the lane

        
    }

}
