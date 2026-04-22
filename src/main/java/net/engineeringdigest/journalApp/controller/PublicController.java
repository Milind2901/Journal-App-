package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // made a bean with endpoint
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService ;

    @GetMapping("/health-check")  // Mapping the fn with a path having GET call
    public String healthCheck(){

        return "OK";

    }

    @PostMapping ("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }
}
