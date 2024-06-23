package com.ferndani00.NFTAggregator.repository;

import com.ferndani00.NFTAggregator.databaseModels.Nft;
import com.ferndani00.NFTAggregator.databaseModels.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private User returnFromSave;

    @BeforeEach
    void init() {
        user1 = new User();
        user1.setBalance(10);
        user1.setPassword("password");
        user1.setEmail("user1@email.com");

        user2 = new User();
        user2.setBalance(20);
        user2.setPassword("password");
        user2.setEmail("user2@email.com");
    }

    @Test
    @DisplayName("Should save user to database and id should not be null")
    void save() {
        returnFromSave = userRepository.save(user1);

        assertNotNull(returnFromSave);
        assertThat(returnFromSave.getUserId()).isNotEqualTo(null);
    }

    @Test
    @DisplayName("Should return record with Id number 2")
    void getById() {
        userRepository.save(user2);
        Optional<User> newUser = userRepository.findById(user2.getUserId());
        assertTrue(newUser.isPresent());
    }

    @Test
    @DisplayName("Should return all records")
    void getAllUsers() {
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> users = userRepository.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Should return null from the saved record")
    void delete() {
        userRepository.save(user1);

        int onceInserted = userRepository.findAll().size();
        userRepository.delete(user1);
        int onceDeleted = userRepository.findAll().size();

        assertEquals(onceInserted, onceDeleted+1);
    }
}
