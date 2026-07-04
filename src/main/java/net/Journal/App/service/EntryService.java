package net.Journal.App.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import net.Journal.App.entity.Entry;
import net.Journal.App.entity.User;
import net.Journal.App.repository.EntryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class EntryService {
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(Entry entry, String userName) {
        User user = userService.findByUserName(userName);
        entry.setDate(LocalDateTime.now());
        Entry saved = entryRepository.save(entry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }

    public void saveEntry(Entry entry) {
        entryRepository.save(entry);
    }

    public List<Entry> getAll() {
        return entryRepository.findAll();
    }

    public Optional<Entry> getEntryById(ObjectId id) {
        return entryRepository.findById(id);
    }

    @Transactional
    public boolean deleteEntry(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
             removed =  user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                entryRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the entry",e);
        }
        return removed;
    }
}
