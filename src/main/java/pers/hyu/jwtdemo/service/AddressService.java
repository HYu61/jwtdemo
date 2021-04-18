package pers.hyu.jwtdemo.service;


import pers.hyu.jwtdemo.share.dto.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAddressListByUserId(String userId);
    AddressDto getAddressById(String addressId);
}
