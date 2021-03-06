package pers.hyu.jwtdemo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pers.hyu.jwtdemo.share.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUser(String email);

    UserDto getUserByUserId(String userId);
    UserDto editUserByUserId(String userId, UserDto editedUserDto);
    void removeByUserId(String userId);

    List<UserDto> getAllUsers(int page, int limit);

    boolean isEmailTokenVerified(String token);

    boolean isValidEmailForResetPassword(String email);

    boolean isValidResetPasswordToken(String token, String password);
}
