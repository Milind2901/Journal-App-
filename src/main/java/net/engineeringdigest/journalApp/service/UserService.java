package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.respository.JournalEntryRepo;
import net.engineeringdigest.journalApp.respository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository; // injects the implementation of journalEntryRepo interface during run-time into the service


    public void saveEntry(User user){           // method to save journal entry into database
        userRepository.save(user);
    }

    public List<User> getAll(){                    // method to get all the entries
        return userRepository.findAll();
    }

    public Optional<User> findbyid(ObjectId id){  // find journal entry by id ( optional means it can have it or not have it )
        return userRepository.findById(id);
    }

    public void deletebyid(ObjectId id){
        userRepository.deleteById(id);
    }

}
