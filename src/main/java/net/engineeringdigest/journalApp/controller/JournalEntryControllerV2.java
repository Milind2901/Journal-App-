package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/journal")   // ye pure class pe mapping kardega
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService; // injected the instance of it

    @Autowired
    private UserService userService ; // created an instance of User Service

    @GetMapping   // localhost:8080/journal GET
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // stores the context of authenticated users credentials
        String userName = authentication.getName();
        User user = userService.findByUserName(userName); // get user from repo by username
        List<JournalEntry> all = user.getJournalEntries(); // find journal entries
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping        // localhost:8080/journal POST
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // stores the context of authenticated users credentials
            String userName = authentication.getName();
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED) ;
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // stores the context of authenticated users credentials
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList()); // get tge list of all journal entries of user , matches the indv. entry with id passed and returns the matched ids as list
        if (!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findbyid(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK); // if response entity has the object it shows and gives OK response
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){ // ? tells wildcard type , which means any type can be returned
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // stores the context of authenticated users credentials
        String userName = authentication.getName();
        boolean removed = journalEntryService.deletebyid(myId, userName);
        if (removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry

    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // stores the context of authenticated users credentials
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList()); // get tge list of all journal entries of user , matches the indv. entry with id passed and returns the matched ids as list
        if (!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findbyid(myId);
            if(journalEntry.isPresent()){
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getTitle().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK) ;
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
