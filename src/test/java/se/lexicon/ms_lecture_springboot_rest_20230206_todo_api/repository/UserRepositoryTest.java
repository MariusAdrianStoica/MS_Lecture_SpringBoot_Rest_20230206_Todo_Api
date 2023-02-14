package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.model.entity.User;

import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    User createdUser1;
    User createdUser2;
    @BeforeEach
    public void setup(){
        User user1 = new User("test.test", "passTest");
        user1.setExpired(false);
        createdUser1 = userRepository.save(user1);
        User user2 = new User("test2.test2", "pwd2");
        user2.setExpired(true);
        createdUser2 = userRepository.save(user2);
    }

    @Test
    public void test_findByUsername(){

        Optional<User> testUser = userRepository.findByUsername("test.test");
        assertNotNull(testUser.get());
        assertEquals(testUser.get(), createdUser1);

    }
    @Test
    public void test_existsByUsername(){
        Boolean existsByUsername = userRepository.existsByUsername("test.test");
        assertEquals(true, existsByUsername);
    }

    // todo: mot working


    /*
    @Test
    public void test_updateExpiredByUsername(){
        userRepository.updateExpiredByUsername("test.test", false);
        assertEquals(true, createdUser1.isExpired());
    }


    @Test
    public void test_resetPassword(){
        userRepository.resetPassword("test.test", "newPass");
        assertEquals("newPass", createdUser1.getPassword());

    }

     */


}
