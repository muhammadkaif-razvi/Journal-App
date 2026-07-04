package net.Journal.App.controller;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import net.Journal.App.entity.Entry;
import net.Journal.App.entity.User;
import net.Journal.App.service.EntryService;
import net.Journal.App.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class EntryController {

    @Autowired
    private EntryService entryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<Entry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public  ResponseEntity<Entry> createEntry(@RequestBody Entry entry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try{
            entryService.saveEntry(entry,userName);
            return new ResponseEntity<>(entry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{id}")
    public ResponseEntity<Entry> getEntryById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user  =   userService.findByUserName(userName);
        List<Entry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
          Optional<Entry> journalEntry = entryService.getEntryById(id);
          if(journalEntry.isPresent()){
              return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
          }
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entry> updateEntry(@PathVariable ObjectId id, @RequestBody Entry entry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<Entry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<Entry> journalEntry = entryService.getEntryById(id);
            if(journalEntry.isPresent()){
                Entry old = journalEntry.get();
                old.setTitle(!entry.getTitle().isEmpty() ? entry.getTitle() : old.getTitle());
                old.setContent(entry.getContent() != null && !entry.getContent().isEmpty() ? entry.getContent() : old.getContent());
                entryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
       boolean removed =  entryService.deleteEntry(id,userName);
       if(removed){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }


}
