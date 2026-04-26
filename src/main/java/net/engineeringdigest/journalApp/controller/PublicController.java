package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController // made a bean with endpoint
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService ;

    @Autowired
    private AuthenticationManager authenticationManager ;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl ;

    @Autowired
    private JWTUtil jwtUtil ;

    @GetMapping("/health-check")  // Mapping the fn with a path having GET call
    public String healthCheck(){

        return "OK";

    }

    @PostMapping ("/signup")  // user signs up and his key credentials are stored
    public void signUp(@RequestBody User user){
        userService.saveNewUser(user);
    }

    @PostMapping ("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try { // basically calls UserDetailsServiceImpl to check username and password and encoder bean to authenticate password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occured while createAuthenticationToken",e);
            return new ResponseEntity<>("Incorrect Username or Password",HttpStatus.BAD_REQUEST);
        }

    }
}
