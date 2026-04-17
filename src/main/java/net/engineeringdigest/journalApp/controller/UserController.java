package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.respository.UserRepository;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/user")   // ye pure class pe mapping kardega
public class UserController {

   @Autowired
    private UserService userService ;

    @GetMapping
   public List<User> getAllUser(){
       return userService.getAll();
   }

   @PostMapping
   public void createUser(@RequestBody User user){
        userService.saveEntry(user);
   }

   @GetMapping("id/{myId}")
   public Optional<User> getUserById(@PathVariable ObjectId myId){
       Optional<User> findById = userService.findbyid(myId);
       return findById ;
   }

   @PutMapping("id/{myId}")
   public User updateUser(@PathVariable ObjectId myId, User user){
        userService.saveEntry(user);
        return user;
   }

   @DeleteMapping("id/{myId}")
    public boolean deleteUserById(@PathVariable ObjectId myId){
        userService.deletebyid(myId);
        return true;
   }




}
