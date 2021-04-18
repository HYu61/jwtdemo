package pers.hyu.jwtdemo.ui.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.hyu.jwtdemo.ui.model.request.LoginRequestModel;

import java.awt.*;

@RestController
public class AuthenticationController {
    @ApiOperation("User login")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Response Headers",
                    responseHeaders = {
                            @ResponseHeader(name = "authorization",
                                    description = "Bearer <JWT value here>"),
                            @ResponseHeader(name = "userId",
                                    description = "<Public User Id value here>")
                    })
    })
    @PostMapping(value = "/users/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void theFakeLogin(@RequestBody LoginRequestModel loginRequestModel)
    {
        throw new IllegalStateException("This method should not be called. This method is implemented by Spring Security");
    }
}
