package pers.hyu.jwtdemo.ui.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pers.hyu.jwtdemo.exception.ErrorMessages;
import pers.hyu.jwtdemo.exception.UserServiceException;
import pers.hyu.jwtdemo.service.AddressService;
import pers.hyu.jwtdemo.service.UserService;
import pers.hyu.jwtdemo.share.dto.AddressDto;
import pers.hyu.jwtdemo.share.dto.UserDto;
import pers.hyu.jwtdemo.share.util.EntityUtil;
import pers.hyu.jwtdemo.ui.model.request.UserDetailsRequestModel;
import pers.hyu.jwtdemo.ui.model.response.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/users")
@Api(tags = "User controller api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;
    @Autowired
    private EntityUtil entityUtil;


    @ApiOperation(value = "Create User Endpoint")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResp createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel) throws IllegalAccessException {


        if (entityUtil.isAnyFieldUnset(userDetailsRequestModel)) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
//        BeanUtils.copyProperties(userDetailsRequestModel, userDto);
        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
//        BeanUtils.copyProperties(createdUser, returnValue);
        UserResp returnValue = new ModelMapper().map(createdUser, UserResp.class);
        return returnValue;
    }

    @ApiOperation(value = "The Get User Details Endpoint",
            notes = "Get the user's detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "Bearer JWT Token", paramType = "header", required = true)
    })
    @GetMapping(path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResp getUserByUserId(@PathVariable String userId) {

        UserDto userDto = userService.getUserByUserId(userId);
//        BeanUtils.copyProperties(userDto, returnValue);
        UserResp returnValue = new ModelMapper().map(userDto, UserResp.class);
        return returnValue;

    }

    @ApiOperation(value = "The Modify User Info Endpoint",
            notes = "Modify the user's detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "Bearer JWT Token", paramType = "header", required = true)
    })
    @PutMapping(path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResp editUserByUserId(@PathVariable String userId,
                                     @RequestBody UserDetailsRequestModel userDetailsRequestModel) {
        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
//        BeanUtils.copyProperties(userDetailsRequestModel, userDto);

        UserDto updatedUser = userService.editUserByUserId(userId, userDto);

//        BeanUtils.copyProperties(updatedUser, returnValue);
        UserResp returnValue = new ModelMapper().map(updatedUser, UserResp.class);

        return returnValue;

    }

    @ApiOperation(value = "The Remove User Details Endpoint",
            notes = "Remove the user's detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "Bearer JWT Token", paramType = "header", required = true)
    })
    @DeleteMapping(path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String userId) {
        userService.removeByUserId(userId);
        OperationStatusModel returnValue = new OperationStatusModel(Operation.DELETE.name(), RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @ApiOperation(value = "The Get All Users Details Endpoint",
            notes = "Get all the user's detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "Bearer JWT Token", paramType = "header", required = true)
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserResp> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "limit", defaultValue = "5") int limit) {
        if (page < 0 || limit < 1) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        List<UserResp> returnValue = new ArrayList<>();
        List<UserDto> userDtoList = userService.getAllUsers(page, limit);

        for (UserDto userDto :
                userDtoList) {

            UserResp userResp = new ModelMapper().map(userDto, UserResp.class);
//            BeanUtils.copyProperties(userDto, userResp);
            returnValue.add(userResp);
        }

        return returnValue;

    }


    // get the user's all address
    @ApiOperation(value = "The Get User's Address List Endpoint",
            notes = "Get the user's address list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "Bearer JWT Token", paramType = "header", required = true)
    })
    @GetMapping(path = "/{userId}/addressList", produces = {MediaType.APPLICATION_JSON_VALUE})
    public CollectionModel<AddressResp> getAddressList(@PathVariable String userId) {


        List<AddressDto> addressDtoList = addressService.getAddressListByUserId(userId);
        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<AddressResp>>() {
        }.getType();
        List<AddressResp> returnValue = modelMapper.map(addressDtoList, listType);

        // add selfLink to the _embedded info
        returnValue.forEach(addressResp -> {
            addressResp.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                    .getAddress(userId, addressResp.getAddressId())).withSelfRel());
        });


        // add links
        Link userLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getUserByUserId(userId)).withRel("userLink");
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getAddressList(userId)).withSelfRel();


        return CollectionModel.of(returnValue, Arrays.asList(userLink, selfLink));

    }

    @ApiOperation(value = "The Get User's Single Address Endpoint",
            notes = "Get the user's single address")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "Bearer JWT Token", paramType = "header", required = true)
    })
    @GetMapping(path = "/{userId}/addressList/{addressId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public EntityModel<AddressResp> getAddress(@PathVariable String userId, @PathVariable String addressId) {
        AddressDto addressDto = addressService.getAddressById(addressId);
        AddressResp returnValue = new ModelMapper().map(addressDto, AddressResp.class);

        // http:localhost:8080/users/<userId>
        Link userLink = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(userId)
                .withRel("userLink");

        // create link with method on
        Link addressListLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getAddressList(userId))
                .withRel("addressListLink");
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getAddress(userId, addressId))
                .withSelfRel();

//        returnValue.add(userLink);
//        returnValue.add(addressListLink);
//        returnValue.add(selfLink);
//        returnValue.add(Arrays.asList(userLink,addressListLink,selfLink));

        // can also user EntityModel.of
        return EntityModel.of(returnValue, Arrays.asList(userLink, addressListLink, selfLink));
    }

    //Get the email verification for user sign up
    @GetMapping(value = "/email_verification", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin("*")
    public OperationStatusModel verifyEmailToken(@RequestParam("token") String token){
        OperationStatusModel result = new OperationStatusModel();
        result.setOperation(Operation.VERIFY_EMAIL.name());

        boolean isVerified = userService.isEmailTokenVerified(token);
        result.setOperationResult(isVerified?RequestOperationStatus.SUCCESS.name():RequestOperationStatus.FAILED.name());

        return result;
    }
}
