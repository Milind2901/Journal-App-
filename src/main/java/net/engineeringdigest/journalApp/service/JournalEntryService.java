package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.respository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo; // injects the implementation of journalEntryRepo interface during run-time into the service


    public void saveEntry(JournalEntry journalEntry){           // method to save journal entry into database
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll(){                    // method to get all the entries
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findbyid(ObjectId id){  // find journal entry by id ( optional means it can have it or not have it )
        return journalEntryRepo.findById(id);
    }

    public void deletebyid(ObjectId id){
         journalEntryRepo.deleteById(id);
    }

}
