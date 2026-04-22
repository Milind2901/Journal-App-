package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "journal_entries")  // this Journal Entry entity is mapped to named collection in repository
//@Getter   lombok annotation that generates get method during compile time
//@Setter   lombok annotation that generates set method during compile time
@Data   // a single annotation to generate getters setters oneargConstructors multiargConstructors etc
@NoArgsConstructor
public class JournalEntry {        // this is called Plain Old Java Object ( POJO )

    @Id
    private ObjectId id ;

    private String title ;

    private String Content ;

    private LocalDateTime date;



}
