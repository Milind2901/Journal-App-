package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "users")  // this Journal Entry entity is mapped to named collection in repository
//@Getter   lombok annotation that generates get method during compile time
//@Setter   lombok annotation that generates set method during compile time
@Data   // a single annotation to generate getters setters oneargConstructors multiargConstructors etc
public class User {        // this is called Plain Old Java Object ( POJO )

    @Id
    private ObjectId Id ;

    @Indexed(unique = true)
    @NonNull
    private String userName ;
    @NonNull
    private String password ;

    @DBRef  // creates a reference of Journal Entries from DB
    private List<JournalEntry> journalEntries = new ArrayList<>();
}
