package net.Journal.App.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import net.Journal.App.repository.UserRepository;
import org.mockito.*;


public class UserDetailsServiceImplTests {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;
    @BeforeEach void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void  loadUserByUserNameTest(){
//      when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("kaif").roles(new ArrayList<>()).password("kdfkaf").build());
//        UserDetails  user = userDetailsService.loadUserByUsername("kaif");
//        Assertions.assertNotNull(user);
    }
}
