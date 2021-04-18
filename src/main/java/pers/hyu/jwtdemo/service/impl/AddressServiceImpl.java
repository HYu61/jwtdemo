package pers.hyu.jwtdemo.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.hyu.jwtdemo.exception.ErrorMessages;
import pers.hyu.jwtdemo.exception.UserServiceException;
import pers.hyu.jwtdemo.io.entity.AddressEntity;
import pers.hyu.jwtdemo.io.repository.AddressRepository;
import pers.hyu.jwtdemo.service.AddressService;
import pers.hyu.jwtdemo.service.UserService;
import pers.hyu.jwtdemo.share.dto.AddressDto;
import pers.hyu.jwtdemo.share.dto.UserDto;


import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @Override
    public List<AddressDto> getAddressListByUserId(String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        if(userDto == null){
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        return userDto.getAddressList();
    }

    @Transactional(readOnly = true)
    @Override
    public AddressDto getAddressById(String addressId) {
        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        if(addressEntity == null){
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return new ModelMapper().map(addressEntity, AddressDto.class);
    }
}
