package com.ead.authuser.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.impl.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@CrossOrigin(maxAge = 3600, origins = "*")
@RequestMapping(path = "/auth")
public class AuthenticationController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @PostMapping(path = "/signup")
    public ResponseEntity<Object> registerUser(
            @RequestBody @Validated(UserDto.UserView.RegistrationPost.class) @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
        if (userServiceImpl.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR: Username is Already Taken.");
        }

        if (userServiceImpl.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR: Email is Already Taken.");
        }

        var userModel = new UserModel();

        BeanUtils.copyProperties(userDto, userModel);

        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        var newUser = userServiceImpl.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

}
