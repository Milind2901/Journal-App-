package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.respository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo; // injects the implementation of journalEntryRepo interface during run-time into the service

    @Autowired
    private UserService userService ;

    @Transactional  // treats the whole block of code as one operation if any step fails whole block fails
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);// method to save journal entry into database
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry); // gets the stored journey entry into saved variable
            user.getJournalEntries().add(saved); // adds the journal entry into the journal entry attribute of user
            userService.saveEntry(user); // saves the user data into repository
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("There is an error occured ",e);
        }

    }

    public void saveEntry(JournalEntry journalEntry){  // method for put mapping in journal entry
        journalEntryRepo.save(journalEntry); // saves the journal entry
    }

    public List<JournalEntry> getAll(){                    // method to get all the entries
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findbyid(ObjectId id){  // find journal entry by id ( optional means it can have it or not have it )
        return journalEntryRepo.findById(id);
    }

    public void deletebyid(ObjectId id, String userName){
        User user = userService.findByUserName(userName);  // find user by username and store in user
        user.getJournalEntries().removeIf(x -> x.getId().equals(id)); // get the journal entries attached to a user and delete those which are matching the id passed
        userService.saveEntry(user); // save the user changes
        journalEntryRepo.deleteById(id); // remove the journal entries from journal entry repository matching the id passed
    }

}
