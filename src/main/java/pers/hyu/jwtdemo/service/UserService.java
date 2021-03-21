package pers.hyu.jwtdemo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pers.hyu.jwtdemo.share.dto.UserDto;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUser(String email);

    UserDto getUserByUserId(String userId);
    UserDto editUserByUserId(String userId, UserDto editedUserDto);
    void removeByUserId(String userId);

}
