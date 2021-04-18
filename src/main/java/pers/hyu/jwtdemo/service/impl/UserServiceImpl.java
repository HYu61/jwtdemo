package pers.hyu.jwtdemo.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.hyu.jwtdemo.exception.ErrorMessages;
import pers.hyu.jwtdemo.exception.UserServiceException;
import pers.hyu.jwtdemo.io.entity.UserEntity;
import pers.hyu.jwtdemo.io.repository.UserRepository;
import pers.hyu.jwtdemo.service.UserService;
import pers.hyu.jwtdemo.share.dto.AddressDto;
import pers.hyu.jwtdemo.share.dto.UserDto;
import pers.hyu.jwtdemo.share.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityUtil entityUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserDto createUser(UserDto userDto) {

        UserEntity storiedUser = userRepository.findByEmail(userDto.getEmail());
        if(storiedUser != null){
            throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }
//        UserEntity newUserEntity = new UserEntity();
        for (int i = 0; i < userDto.getAddressList().size(); i++) {
            AddressDto addressDto = userDto.getAddressList().get(i);
            addressDto.setAddressId(entityUtil.generateId(30));
            addressDto.setUserDetail(userDto);
            userDto.getAddressList().set(i, addressDto);
        }


        userDto.setUserId(entityUtil.generateId(30));
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

//        BeanUtils.copyProperties(userDto, newUserEntity);

        UserEntity newUserEntity = new ModelMapper().map(userDto,UserEntity.class);

        UserEntity storedUser = userRepository.save(newUserEntity);
        UserDto newUserDto = new ModelMapper().map(storedUser, UserDto.class);
//        BeanUtils.copyProperties(storedUser, newUserDto);
        return newUserDto;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        UserDto returnValue = new ModelMapper().map(userEntity, UserDto.class);
//        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if(user == null){
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserDto result = new ModelMapper().map(user, UserDto.class);
//        BeanUtils.copyProperties(user, result);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserDto editUserByUserId(String userId, UserDto editedUserDto) {


        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        userEntity.setFirstName(editedUserDto.getFirstName());
        userEntity.setLastName(editedUserDto.getLastName());
        userEntity.setEmail(editedUserDto.getEmail());
        UserEntity updatedUserEntity = userRepository.save(userEntity);


        UserDto returnValue = new ModelMapper().map(updatedUserEntity, UserDto.class);
//        BeanUtils.copyProperties(updatedUserEntity, returnValue);
        return returnValue;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void removeByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        userRepository.delete(userEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();

        // the first page is 0
        if (page > 0) {
            page -= 1;
        }
        Pageable pageResult = PageRequest.of(page, limit);
        Page<UserEntity> userEntityPage = userRepository.findAll(pageResult);
        List<UserEntity> userEntityList = userEntityPage.getContent();

        for (UserEntity userEntity : userEntityList
        ) {
            UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
//            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }
        return returnValue;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null){
            throw new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),new ArrayList<>());
    }
}
