package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    UserDto saveUser(UserDto userDto);

    UserDto getById(long id);

    UserDto getByEmail(String email);

    void changeBalance(double amount, UserDto userDto);
}
