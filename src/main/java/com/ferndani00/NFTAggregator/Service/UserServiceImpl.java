package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.dto.UserDto;
import com.ferndani00.NFTAggregator.models.databaseModels.Role;
import com.ferndani00.NFTAggregator.models.databaseModels.User;
import com.ferndani00.NFTAggregator.repository.RoleRepository;
import com.ferndani00.NFTAggregator.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl(UserRepository userRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role;
        if(userRepository.findAll().isEmpty()) {
            initiateRoles();
            role = roleRepository.findByName("ROLE_ADMIN");
        } else {
            role = roleRepository.findByName("ROLE_USER");
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public UserDto getById(long id) {
        return mapToUserDto(userRepository.getReferenceById(id));
    }

    // hier roep ik 2x de db aan, wil dit maar 1 x doen
    @Override
    public UserDto findByEmail(String email) {
        UserDto userDto = new UserDto();
        if(userRepository.findByEmail(email) != null) {
            userDto = mapToUserDto(userRepository.findByEmail(email));
        }
        System.out.println(userDto.getEmail());
        return userDto;
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setBalance(user.getBalance());
        //userDto.setFavoriteNfts(user.getFavoritedNfts());
        //userDto.setOwnedNfts(user.getOwnedNfts());
        //userDto.setNftsInCart(user.getNftsInCart());
        return userDto;
    }

    private User mapToUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setBalance(userDto.getBalance());
        //user.setOwnedNfts(userDto.getOwnedNfts());
        //user.setFavoritedNfts(userDto.getFavoriteNfts());
        //user.setNftsInCart(userDto.getNftsInCart());
        return user;
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    private void initiateRoles() {
        Role admin = new Role();
        admin.setName("ROLE_ADMIN");
        Role user = new Role();
        user.setName("ROLE_USER");
        roleRepository.save(admin);
        roleRepository.save(user);
    }
}