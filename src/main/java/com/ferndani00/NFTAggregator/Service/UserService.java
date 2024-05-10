package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    void saveUser(UserDto userDto);
    UserDto getById(long id);
    UserDto findByEmail(String email);
}
