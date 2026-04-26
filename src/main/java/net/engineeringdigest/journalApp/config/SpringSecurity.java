package net.engineeringdigest.journalApp.config;


import net.engineeringdigest.journalApp.filter.JwtFilter;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity  {  // Security configuration class that grants customizable security

    @Autowired
    private UserDetailsServiceImpl userDetailsService ;

    @Autowired
    private JwtFilter jwtFilter ;

    // Configuring endpoint security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
         http.authorizeRequests()
                .antMatchers("/journal/**","/users/**").authenticated() // check authentication of all requests with this url pattern
                .anyRequest()
                 .permitAll();  // permit all other requests - no auth required
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable() ;
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // filter chain mei pehle JWT filter chalega uske baad user password authentication filter chalega aur fir badd mei controllers chalenge
        return http.build();
    }

    // Manually configuring the user details and password Authentication manager
    @Bean  // basically matches the passcode and username to authenticate the user
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsServiceImpl userDetailsService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder()) // converts the raw password to the hashcode to match with password stored in database
                .and()
                .build();
    }


    @Bean // converts password into hashcode
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
