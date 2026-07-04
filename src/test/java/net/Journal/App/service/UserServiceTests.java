package net.Journal.App.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.Journal.App.repository.UserRepository;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;

//    @ParameterizedTest
//    @ArgumentsSource(UserArgumentProvider.class)
//    public void testAdd(User user){
//        assertTrue(userService.saveNewUser(user));
//    }



}
