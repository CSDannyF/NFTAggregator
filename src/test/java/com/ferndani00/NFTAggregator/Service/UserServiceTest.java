package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.databaseModels.Nft;
import com.ferndani00.NFTAggregator.databaseModels.User;
import com.ferndani00.NFTAggregator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void init() {
        List<Nft> ownedNfts = new ArrayList<>();
        List<Nft> cart = new ArrayList<>();
        List<Nft> favoritNfts = new ArrayList<>();

        user1 = new User();
        user1.setBalance(10);
        user1.setPassword("password");
        user1.setEmail("user1@email.com");
        user1.setFavoritedNfts(favoritNfts);
        user1.setOwnedNfts(ownedNfts);
        user1.setNftsInCart(cart);

        user2 = new User();
        user2.setBalance(20);
        user2.setPassword("password");
        user2.setEmail("user2@email.com");
        user2.setFavoritedNfts(favoritNfts);
        user2.setOwnedNfts(ownedNfts);
        user2.setNftsInCart(cart);

        user3 = new User();
        user3.setBalance(30);
        user3.setPassword("password");
        user3.setEmail("user3@email.com");
        user3.setFavoritedNfts(favoritNfts);
        user3.setOwnedNfts(ownedNfts);
        user3.setNftsInCart(cart);
    }

    @Test
    @DisplayName("Should save User")
    void save() {
        when(userRepository.save(any(User.class))).thenReturn(user1);

        User newUser = userRepository.save(user1);

        assertNotNull(user1);
        assertThat(newUser.getEmail()).isEqualTo("user1@email.com");
    }

    @Test
    @DisplayName("Should return a list of users")
    void getUsers() {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        when(userRepository.findAll()).thenReturn(users);

        assertNotNull(userService.getAll());
        assertEquals(3, userService.getAll().size());
    }

    @Test
    @DisplayName("Should throw runtime exception")
    void getByIdShouldReturnException() {
        user1.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        assertThrows(RuntimeException.class,() -> {
            userService.getById(2L);
        });
    }
}
