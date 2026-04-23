package net.engineeringdigest.journalApp.controller;



import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("/user")   // ye pure class pe mapping kardega
public class UserController {

   @Autowired
    private UserService userService ;


    // these two endppoints are authorised and work only after user has been authenticated from Database
   @PutMapping   // Updating content based on new username and password
   public ResponseEntity<?> updateUser(@RequestBody User user ){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // SecurityContextHolder stores the context of authenticated users credentials
       String userName = authentication.getName();
       User userInDb = userService.findByUserName(userName);  // getting the value of the user
       userInDb.setUserName(user.getUserName()); // setting the new username
       userInDb.setPassword(user.getPassword()); // setting the new password
       userService.saveNewUser(userInDb);  // saving new details
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

    @DeleteMapping   // Deleting content based on new username and password
    public ResponseEntity<?> deleteUser(@RequestBody User user ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // stores the context of authenticated users credentials
        String userName = authentication.getName();
        userService.deleteByuserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
