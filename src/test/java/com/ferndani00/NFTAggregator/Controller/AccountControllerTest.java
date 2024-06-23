package com.ferndani00.NFTAggregator.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferndani00.NFTAggregator.Service.UserServiceImpl;
import com.ferndani00.NFTAggregator.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should add to balance")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("user@email.com");
        userDto.setPassword("password");
        userDto.setBalance(100.0);

        when(userService.getByEmail(ArgumentMatchers.anyString())).thenReturn(userDto);
        when(userService.getAll()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(post("/account/addToBalance")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("amount", "50.0"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/account"));
    }
}
