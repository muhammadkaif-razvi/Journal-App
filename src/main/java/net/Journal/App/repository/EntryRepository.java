package net.Journal.App.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import net.Journal.App.entity.Entry;

public interface EntryRepository extends MongoRepository<Entry, ObjectId> {

}
