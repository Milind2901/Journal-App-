package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRespository ;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // user class for authentication and authorization

        User user = userRespository.findUsersByUserName(username); // getting the user from repo
        if (user!= null){  // if the user is found we get the user details , build them and return under userDetails variable
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()

                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0])) // converted the roles list into Array with default size 0
                    .build();
            return userDetails;
        }
        throw new UsernameNotFoundException("User is not found with username : " + username);
    }




}
