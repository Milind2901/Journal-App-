package net.engineeringdigest.journalApp.respository;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;




public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId> {  // expects entity and id's datatype

}