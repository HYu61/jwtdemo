package pers.hyu.jwtdemo.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pers.hyu.jwtdemo.exception.ErrorMessages;
import pers.hyu.jwtdemo.exception.UserServiceException;
import pers.hyu.jwtdemo.service.UserService;
import pers.hyu.jwtdemo.share.dto.UserDto;
import pers.hyu.jwtdemo.share.util.EntityUtil;
import pers.hyu.jwtdemo.ui.model.request.UserDetailsRequestModel;
import pers.hyu.jwtdemo.ui.model.response.Operation;
import pers.hyu.jwtdemo.ui.model.response.OperationStatusModel;
import pers.hyu.jwtdemo.ui.model.response.RequestOperationStatus;
import pers.hyu.jwtdemo.ui.model.response.UserResp;



@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EntityUtil entityUtil;


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserResp createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel) throws IllegalAccessException {
        UserResp returnValue = new UserResp();
        UserDto userDto = new UserDto();
        if(entityUtil.isAnyFieldUnset(userDetailsRequestModel)){
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        BeanUtils.copyProperties(userDetailsRequestModel, userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);
        return returnValue;
    }

    @GetMapping(path = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserResp getUserByUserId(@PathVariable String userId){
        UserResp returnValue = new UserResp();
        UserDto userDto = userService.getUserByUserId(userId);
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;

    }

    @PutMapping(path = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserResp editUserByUserId(@PathVariable String userId,
                                     @RequestBody UserDetailsRequestModel userDetailsRequestModel){
        UserResp returnValue = new UserResp();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetailsRequestModel, userDto);

        UserDto updatedUser = userService.editUserByUserId(userId, userDto);

        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;

    }

    @DeleteMapping(path = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String userId) {
        userService.removeByUserId(userId);
        OperationStatusModel returnValue = new OperationStatusModel(Operation.DELETE.name(), RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }
}
