package com.ferndani00.NFTAggregator.Service;

import com.ferndani00.NFTAggregator.dto.UserDto;
import com.ferndani00.NFTAggregator.databaseModels.Role;
import com.ferndani00.NFTAggregator.databaseModels.User;
import com.ferndani00.NFTAggregator.repository.RoleRepository;
import com.ferndani00.NFTAggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final NftServiceImpl nftService;

    @Autowired
    private UserServiceImpl(UserRepository userRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder,
                            NftServiceImpl nftService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.nftService = nftService;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role;

        if (userRepository.findAll().isEmpty()) {
            initiateRoles();
            role = roleRepository.findByName("ROLE_ADMIN");
        } else {
            role = roleRepository.findByName("ROLE_USER");
        }
        user.setRoles(Arrays.asList(role));
        User newUser = userRepository.save(user);
        return mapToUserDto(newUser);
    }

    @Override
    public UserDto getById(long id) {
        return mapToUserDto(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public UserDto getByEmail(String email) {
        UserDto userDto = new UserDto();
        if (userRepository.findByEmail(email) != null) {
            userDto = mapToUserDto(userRepository.findByEmail(email));
        }
        return userDto;
    }

    @Override
    public void changeBalance(double amount, UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        user.setBalance(userDto.getBalance() + amount);
        userRepository.save(user);
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setBalance(user.getBalance());

        NftServiceImpl nftService1 = new NftServiceImpl();
        if (user.getFavoritedNfts() != null) {

            userDto.setFavoriteNfts(nftService1.mapModelToDtoList(user.getFavoritedNfts()));
        }

        if (user.getOwnedNfts() != null && !user.getOwnedNfts().isEmpty()) {
            userDto.setOwnedNfts(nftService1.mapModelToDtoList(user.getOwnedNfts()));
        }
        if (user.getNftsInCart() != null)
        userDto.setNftsInCart(nftService1.mapModelToDtoList(user.getNftsInCart()));
        return userDto;
    }

    private Role checkRoleExist() {
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
